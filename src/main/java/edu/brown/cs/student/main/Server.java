package edu.brown.cs.student.main;

import static spark.Spark.after;

import edu.brown.cs.student.main.CSVData.BroadbandDatasource;
import edu.brown.cs.student.main.CSVData.CSVData;
import edu.brown.cs.student.main.CSVData.CSVDatasource;
import edu.brown.cs.student.main.CSVData.MockedBroadband;
import edu.brown.cs.student.main.Handlers.BroadbandHandler;
import edu.brown.cs.student.main.Handlers.LoadHandler;
import edu.brown.cs.student.main.Handlers.SearchHandler;
import edu.brown.cs.student.main.Handlers.ViewHandler;
import spark.Spark;

public class Server {
  private final CSVDatasource CSVstate;
  private final BroadbandDatasource broadbandState;

  public static void main(String[] args) {
    new Server(new CSVData(), new MockedBroadband()).run();
  }

  private Server(CSVDatasource CSVstate, BroadbandDatasource broadbandstate) {
    this.CSVstate = CSVstate;
    this.broadbandState = broadbandstate;
    this.run();
  }

  private void run() {
    int port = 3232;
    Spark.port(port);

    after(
        (request, response) -> {
          response.header("Access-Control-Allow-Origin", "*");
          response.header("Access-Control-Allow-Methods", "*");
        });

    Spark.get("load", new LoadHandler(this.CSVstate));
    Spark.get("search", new SearchHandler(this.CSVstate));
    Spark.get("view", new ViewHandler(this.CSVstate));
    Spark.get("broadband", new BroadbandHandler(this.broadbandState));
    Spark.init();
    Spark.awaitInitialization();
    System.out.println("Server started at http://localhost:" + port);
  }
}
