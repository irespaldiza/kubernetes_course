import os
import socket
from http.server import BaseHTTPRequestHandler, HTTPServer


class Handler(BaseHTTPRequestHandler):
    hostname = socket.gethostname()
    message = os.getenv("MESSAGE", "")
    secret_message = os.getenv("SECRET_MESSAGE", "")

    def do_GET(self):
        if self.path == "/health":
            self.send_response(200)
            self.send_header("Content-Type", "text/plain; charset=utf-8")
            self.end_headers()
            self.wfile.write(b"ok\n")
            return

        if self.path == "/":
            body = (
                f"This is the pod {self.hostname}, "
                f"MESSAGE: {self.message}, "
                f"SECRET_MESSAGE: {self.secret_message}\n"
            )
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
