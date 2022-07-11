//
//  server.swift
//  systemMonitor
//
//  Created by Иван Горшечников on 21.06.2022.
//

import Foundation
import PerfectHTTP
import PerfectHTTPServer


// An example request handler.
// This 'handler' function can be referenced directly in the configuration below.
func startHandler(request: HTTPRequest, response: HTTPResponse) {
    let queue = DispatchQueue.global(qos: .default)
    queue.sync {
        queue.async {
            startMonitoring(interval: Int(request.queryParams[0].1) ?? 1)
        }
    // Respond with a simple message.
    response.setHeader(.contentType, value: "text/html")
    response.appendBody(string: "<html><title>Hello, world!</title><body>start monitoring!</body></html>")
    // Ensure that response.completed() is called when your processing is done.
    response.completed()
    }
    
}

func endHandler(request: HTTPRequest, response: HTTPResponse) {
    let queue = DispatchQueue.global(qos: .default)
    queue.sync {
        queue.async {
            endMonitoring()
        }
    // Respond with a simple message.
    response.setHeader(.contentType, value: "text/html")
    response.appendBody(string: "<html><title>Hello, world!</title><body>end monitoring!</body></html>")
    // Ensure that response.completed() is called when your processing is done.
    response.completed()
    }
    
}


// Configure one server which:
//    * Serves the hello world message at <host>:<port>/
//    * Serves static files out of the "./webroot"
//        directory (which must be located in the current working directory).
//    * Performs content compression on outgoing data when appropriate.
func startServer()
{
    do
    {
    var routes = Routes()

routes.add(method: .get, uri: "/monitoring/start", handler: startHandler)
routes.add(method: .get, uri: "/monitoring/end", handler: endHandler)
try HTTPServer.launch(name: "localhost",
                      port: 8081,
                      routes: routes,
                      responseFilters: [
                        (PerfectHTTPServer.HTTPFilter.contentCompression(data: [:]), HTTPFilterPriority.high)])
    }
    catch{
        print(error)
    }
    
}

startServer()
