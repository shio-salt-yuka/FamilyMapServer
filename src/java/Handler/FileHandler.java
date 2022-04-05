package java.Handler;

import java.Service.user_register;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import passoffrequest.RegisterRequest;
import passoffresult.RegisterResult;

import javax.swing.text.html.HTML;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;

public class FileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try{
            if(exchange.getRequestMethod().toUpperCase().equals("GET")) {
                String urlPath = exchange.getRequestURI().toString();
                if(urlPath.equals("/") | urlPath == null){
                    urlPath = "/index.html";
                }
                //URL path to filePath
                String filePath = "web" + urlPath;

                File f = new File(filePath);
                if(f.exists()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                }else{
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    f = new File("web/HTML/404.html");
                }
                OutputStream respBody = exchange.getResponseBody();
                Files.copy(f.toPath(), respBody);
                exchange.getResponseBody().close();

                success = true;
            }
            if(!success){
                //error code 405
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
