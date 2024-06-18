package org.example.upload.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.example.upload.model.Image;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@MultipartConfig(maxFileSize = 1024*1024*10)
@WebServlet(name = "upload",value = "/upload")
public class UploadServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uploadPath = getServletContext().getRealPath("/") + "image";
        File file = new File(uploadPath);
        if (!file.exists()) {
            file.mkdir();
        }
        List<Image> images = new ArrayList<>();
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    images.add(new Image(f.getName(), req.getContextPath() + "/image/" + f.getName()));
                }
            }
        }

        req.setAttribute("images", images);
        req.getRequestDispatcher("form-upload.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uploadPath = getServletContext().getRealPath("/")+"image";

        File file = new File(uploadPath);
        if (!file.exists()){
            file.mkdir();
        }

        Part image = req.getPart("image");

        String fileName = image.getSubmittedFileName();

        image.write(uploadPath+File.separator+fileName);

        resp.sendRedirect(req.getContextPath()+"/image/"+fileName);

    }
}
