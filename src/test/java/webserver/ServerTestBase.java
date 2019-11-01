package webserver;

import majorPlanner.Controller;

class ServerTestBase {
  public static final int PORT = 6620;

  static {
    startServer();
    WebClient.port = PORT;
  }

  protected static TestWebServer webServer;

  private static void startServer() {
    webServer = new TestWebServer(PORT);
    webServer.start();
  }

  protected void setRequestHandler(Controller requestHandler) {
    webServer.setRequestHandler(requestHandler);
  }
}
