import Yaml
import Foundation
import PostgresClientKit
import SystemKit


enum MyError: Error {
  case InvalidConfig
  case OutOfRange
}

let queue = DispatchQueue.global(qos: .default)

var sys = System()
var idNode :Int = -1;

func getMaxIdNode(connection : Connection, table: String, column: String) throws -> Int {
    let text = "SELECT max(\(column)) FROM \(table);"
    let statement = try connection.prepareStatement(text: text)
    defer {statement.close()}
    
    let cursor = try statement.execute()
    defer {cursor.close()}
    let row = try cursor.next()?.get().columns[0]
    if row?.rawValue == nil
    {return 0}
    else {return (row?.rawValue as! NSString).integerValue}
}

func openConnection(configPath : String) throws -> Connection {
    var configuration = PostgresClientKit.ConnectionConfiguration()
    
    let fileContent = try? NSString(contentsOfFile: configPath, encoding: String.Encoding.utf8.rawValue)

    let value = try! Yaml.load(fileContent! as String)["database"]
    
    if ((value["host"] == nil) || value["port"] == nil || value["ssl"] == nil || value["name"] == nil || value["user"] == nil)
    {
        throw MyError.InvalidConfig
    }
    
    configuration.host = value["host"].string!
    configuration.port = value["port"].int!
    configuration.ssl = value["ssl"].bool!
    configuration.database = value["name"].string!
    configuration.user = value["user"].string!

    let connection = try PostgresClientKit.Connection(configuration: configuration)
    return connection
}

func startRecord(connection:Connection, idNode: Int) throws {
    let sql = "insert into nodes (id_node, \"RAM_size\", start_time) values (\(idNode), \(System.physicalMemory() * 1024), '\(Date().postgresTimestampWithTimeZone)')"
    let statement = try connection.prepareStatement(text: sql).execute()
    defer { statement.close() }
}

func endRecord(connection:Connection, idNode: Int) throws {
    let sql = "update nodes set end_time = '\(Date().postgresTimestampWithTimeZone)' where id_node = \(idNode)"
    let statement = try connection.prepareStatement(text: sql).execute()
    defer { statement.close() }
}

func enterRecords(connection : Connection, idNodes: Int, interval: Int) throws {
    var idNodeLoadDump = try! getMaxIdNode(connection: connection, table: "node_load_dump", column: "id_record") + 1
    
    let cpuUsage = sys.usageCPU()
    let memoryUsage = System.memoryUsage()
    let sql = "insert into node_load_dump (id_record, id_node, \"CPU_usage\", \"RAM_usage\", timestamp) values(\(idNodeLoadDump), \(idNodes), \(100 - cpuUsage.idle), \(100 * (System.physicalMemory() - memoryUsage.inactive - memoryUsage.free)/System.physicalMemory()), '\(Date().postgresTimestampWithTimeZone)')"
    let statement = try connection.prepareStatement(text: sql).execute()
    defer { statement.close() }
    idNodeLoadDump += 1
    sleep(UInt32(interval))
}

struct Load {
    let CPU : Double
    let RAM : Double
    let Time : PostgresTimestampWithTimeZone
}

func getLoad() -> Load {
    let usageCPU = sys.usageCPU()
    let memoryUsage = System.memoryUsage()
    return Load(CPU: 100 - usageCPU.idle , RAM: 100 * (System.physicalMemory() - memoryUsage.inactive - memoryUsage.free)/System.physicalMemory(), Time: Date().postgresTimestampWithTimeZone)
    
}

func startMonitoring() throws {
    let connection = try openConnection(configPath: filePathGlobal + "/config/config.yaml")
    defer {connection.close()}
    idNode = try! getMaxIdNode(connection: connection, table: "nodes", column: "id_node") + 1

    try startRecord(connection: connection, idNode: idNode)
}


func enterRecords(interval: Int) {
    do {
        let connection = try openConnection(configPath: filePathGlobal + "/config/config.yaml")
        defer {connection.close()}
        try enterRecords(connection: connection, idNodes: idNode, interval: interval)
    }
    catch {
        print(error)
    }
}

func endMonitoring() throws {
    let connection = try openConnection(configPath: filePathGlobal + "/config/config.yaml")
    defer {connection.close()}
    try endRecord(connection: connection, idNode: idNode)
}
