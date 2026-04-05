import os
import socket
from http.server import BaseHTTPRequestHandler, HTTPServer


REQUESTS_TOTAL = 0


class Handler(BaseHTTPRequestHandler):
    hostname = socket.gethostname()
    message = os.getenv("MESSAGE", "module-6-metrics-demo")

    def do_GET(self):
        global REQUESTS_TOTAL
        REQUESTS_TOTAL += 1

        if self.path == "/health":
            self.send_response(200)
            self.send_header("Content-Type", "text/plain; charset=utf-8")
            self.end_headers()
            self.wfile.write(b"ok\n")
            return

        if self.path == "/metrics":
            body = (
                "# HELP demo_requests_total Total HTTP requests handled by the demo app\n"
                "# TYPE demo_requests_total counter\n"
                f'demo_requests_total{{app="metrics-demo"}} {REQUESTS_TOTAL}\n'
            )
            encoded = body.encode("utf-8")
            self.send_response(200)
            self.send_header("Content-Type", "text/plain; version=0.0.4; charset=utf-8")
            self.send_header("Content-Length", str(len(encoded)))
            self.end_headers()
            self.wfile.write(encoded)
            return

        if self.path == "/":
            body = f"This is the pod {self.hostname}, MESSAGE: {self.message}\n"
            encoded = body.encode("utf-8")
            self.send_response(200)
            self.send_header("Content-Type", "text/plain; charset=utf-8")
            self.send_header("Content-Length", str(len(encoded)))
            self.end_headers()
            self.wfile.write(encoded)
            return

        self.send_response(404)
        self.send_header("Content-Type", "text/plain; charset=utf-8")
        self.end_headers()
        self.wfile.write(b"not found\n")

    def log_message(self, format, *args):
        return


def main():
    port = int(os.getenv("PORT", "8080"))
    server = HTTPServer(("0.0.0.0", port), Handler)
    print(f"Listening on :{port}", flush=True)
    server.serve_forever()


if __name__ == "__main__":
    main()
