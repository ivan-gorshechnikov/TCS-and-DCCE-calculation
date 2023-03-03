import Foundation
import PerfectHTTP
import PerfectHTTPServer
import ArgumentParser

var isRun : Bool = false;
var filePathGlobal: String = "";

struct Parser: ParsableCommand {
    @Argument() var filePath: String
    
    func run() {
        filePathGlobal = filePath
        startServer()
    }
}

func startHandler(request: HTTPRequest, response: HTTPResponse) {
    if !isRun {
        isRun = true
        let queue = DispatchQueue.global(qos: .default)
        queue.sync {
            response.setHeader(.contentType, value: "json")
            response.setHeader(HTTPResponseHeader.Name.accessControlAllowOrigin, value: "*")
            do { try startMonitoring() }
            catch {
                response.completed(status: HTTPResponseStatus.internalServerError)
                return
            }
            queue.async {
                enterRecords(interval: Int(request.queryParams[0].1) ?? 1)
            }
            print("Start monitoring")
            response.completed()
        }
    } else {
        response.setHeader(.contentType, value: "json")
        response.setHeader(HTTPResponseHeader.Name.accessControlAllowOrigin, value: "*")
        response.completed(status: HTTPResponseStatus.badRequest)
    }
}

func endHandler(request: HTTPRequest, response: HTTPResponse) {
    if isRun {
        isRun = false
        let queue = DispatchQueue.global(qos: .default)
        queue.sync {
            do { try endMonitoring() }
            catch {
                response.completed(status: HTTPResponseStatus.internalServerError)
                return
            }
            response.setHeader(.contentType, value: "json")
            response.setHeader(HTTPResponseHeader.Name.accessControlAllowOrigin, value: "*")
            response.appendBody(string: "{\"text\": \"end\"}")
            print("End monitoring")
            response.completed()
        }
    } else {
        response.setHeader(.contentType, value: "json")
        response.setHeader(HTTPResponseHeader.Name.accessControlAllowOrigin, value: "*")
        response.completed(status: HTTPResponseStatus.badRequest)
    }
}


func loadHandler(request: HTTPRequest, response: HTTPResponse) {
    response.setHeader(.contentType, value: "json/application")
    response.setHeader(HTTPResponseHeader.Name.accessControlAllowOrigin, value: "*")
    let load = getLoad()
    response.appendBody(string: "{\"CPU\":\(load.CPU), \"RAM\":\(load.RAM), \"Time\":\"\(load.Time)\"}")
    print("get load")
    response.completed()
}

func startServer() {
    do {
        var routes = Routes()
            routes.add(method: .get, uri: "/monitoring/get", handler: loadHandler)
            routes.add(method: .get, uri: "/monitoring/start", handler: startHandler)
            routes.add(method: .get, uri: "/monitoring/end", handler: endHandler)
            try HTTPServer.launch(name: "localhost",
                          port: 8081,
                          routes: routes,
                          responseFilters: [
                          (PerfectHTTPServer.HTTPFilter.contentCompression(data: [:]), HTTPFilterPriority.high)])
    } catch {
        print(error)
    }
}

Parser.main()
