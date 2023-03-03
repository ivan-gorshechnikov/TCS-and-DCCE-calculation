import Foundation
import PerfectHTTP
import PerfectHTTPServer

func startHandler(request: HTTPRequest, response: HTTPResponse) {
    let queue = DispatchQueue.global(qos: .default)
    queue.sync {
        queue.async {
            startMonitoring(interval: Int(request.queryParams[0].1) ?? 1)
        }
    response.setHeader(.contentType, value: "text/html")
    response.appendBody(string: "<html><title>Hello, world!</title><body>start monitoring!</body></html>")
    response.completed()
    }
}

func endHandler(request: HTTPRequest, response: HTTPResponse) {
    let queue = DispatchQueue.global(qos: .default)
    queue.sync {
        queue.async {
            endMonitoring()
        }
    response.setHeader(.contentType, value: "text/html")
    response.appendBody(string: "<html><title>Hello, world!</title><body>end monitoring!</body></html>")
    response.completed()
    }
}

func startServer()
{
    do {
        var routes = Routes()
        routes.add(method: .get, uri: "/monitoring/start", handler: startHandler)
        routes.add(method: .get, uri: "/monitoring/end", handler: endHandler)
        try HTTPServer.launch(name: "localhost",
                          port: 8081,
                          routes: routes,
                          responseFilters: [
                          (PerfectHTTPServer.HTTPFilter.contentCompression(data: [:]), HTTPFilterPriority.high)])
    }
    catch {
        print(error)
    }
}

startServer()
