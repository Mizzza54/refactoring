package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private final ProductDao productDao;

    public QueryServlet(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        switch (command) {
            case "max" -> {
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with max price: </h1>");
                productDao.getProductWithMaxPrice().ifPresent(p ->
                        {
                            try {
                                response.getWriter().println(p.getName() + "\t" + p.getPrice() + "</br>");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
                response.getWriter().println("</body></html>");
            }
            case "min" -> {
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with min price: </h1>");
                productDao.getProductWithMinPrice().ifPresent(p ->
                        {
                            try {
                                response.getWriter().println(p.getName() + "\t" + p.getPrice() + "</br>");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
                response.getWriter().println("</body></html>");
            }
            case "sum" -> {
                response.getWriter().println("<html><body>");
                response.getWriter().println("Summary price: ");
                response.getWriter().println(productDao.getProductsSummaryPrice());
                response.getWriter().println("</body></html>");
            }
            case "count" -> {
                response.getWriter().println("<html><body>");
                response.getWriter().println("Number of products: ");
                response.getWriter().println(productDao.getProductsCount());
                response.getWriter().println("</body></html>");
            }
            default -> response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
