package ro.satrapu.codecamp.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DemoEndpoint", urlPatterns = {"/"})
public class DemoEndpoint extends HttpServlet {
  @Inject
  private PersonPersistenceService personPersistenceService;

  @Inject
  private EnvironmentService environmentService;

  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request  servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException      if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request  servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException      if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    throw new ServletException(new UnsupportedOperationException("POST verb not supported"));
  }

  /**
   * Processes HTTP requests.
   *
   * @param request  servlet request
   * @param response servlet response
   */
  private void processRequest(HttpServletRequest request, HttpServletResponse response) {
    response.setContentType("text/html; charset=UTF-8");

    try (PrintWriter printWriter = response.getWriter()) {
      printWriter.println("<!DOCTYPE html>");
      printWriter.println("<html>");
      printWriter.println("<head>");
      printWriter.println("<title>Codecamp - Cluj-Napoca - 2016 - Demo</title>");
      printWriter.println("</head>");
      printWriter.println("<body>");
      printWriter.println("<h1>Codecamp - Cluj-Napoca - 2016 - Demo</h1>");
      printWriter.println("<h2>DemoEndpoint</h2>");
      printWriter.println("<hr/>");

      printWriter.println(String.format("<h3>PIPELINE_STAGE=%s</h3>", environmentService.getPipelineStage()));
      printWriter.println("<hr/>");

      writeInfo(getRequestInfo(request), printWriter);
      printWriter.println("<hr/>");

      writePersons(personPersistenceService.getPersons(), printWriter);

      printWriter.println("</body>");
      printWriter.println("</html>");
    } catch (IOException e) {
      throw new RuntimeException("Unable to process current HTTP request", e);
    }
  }

  /**
   * Extracts info out of a given {@link HttpServletRequest} instance.
   *
   * @param request THe HTTP request containing information.
   * @return A map containing info related to the given request.
   */
  private Map<String, String> getRequestInfo(HttpServletRequest request) {
    Map<String, String> result = new LinkedHashMap<>();
    result.put("Server IP", request.getLocalAddr());
    result.put("Server name", request.getLocalName());
    result.put("Server port", Integer.toString(request.getLocalPort()));
    result.put("Server Java version", System.getProperty("java.version"));
    result.put("Client IP", request.getRemoteAddr());
    result.put("Client name", request.getRemoteHost());
    result.put("Request URI", request.getRequestURI());
    result.put("Request method", request.getMethod());
    result.put("Current date", DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()));

    return result;
  }

  /**
   * Writes a map using a given {@link PrintWriter} instance.
   *
   * @param info        The map to be written.
   * @param printWriter The {@link PrintWriter} used for writing.
   */
  private void writeInfo(Map<String, String> info, PrintWriter printWriter) {
    printWriter.println("<h3>Request info</h3>");
    printWriter.println("<ul>");

    for (Map.Entry<String, String> entry : info.entrySet()) {
      printWriter.println("<li>");
      printWriter.println(String.format("%s: %s", entry.getKey(), entry.getValue()));
      printWriter.println("</li>");
    }

    printWriter.println("</ul>");
  }

  /**
   * Writes a list of {@link Person} entities using a given {@link PrintWriter} instance.
   *
   * @param persons     The entities to be written.
   * @param printWriter The {@link PrintWriter} used for writing.
   */
  private void writePersons(List<Person> persons, PrintWriter printWriter) {
    printWriter.println("<h3>Database records</h3>");

    printWriter.println("<table>");

    printWriter.println("<thead>");
    printWriter.println("<td>ID</td>");
    printWriter.println("<td>Person Name</td>");
    printWriter.println("</thead>");

    printWriter.println("<tbody>");

    for (Person person : persons) {
      printWriter.println("<tr>");

      printWriter.println(String.format("<td>%d</td>", person.getId()));
      printWriter.println(String.format("<td>%s %s</td>", person.getFirstName(), person.getLastName()));

      printWriter.println("</tr>");
    }

    printWriter.println("</tbody>");

    printWriter.println("</table>");
  }
}