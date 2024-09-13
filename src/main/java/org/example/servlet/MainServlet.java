package org.example.servlet;
import org.example.controller.PostController;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.example.repository.PostRepository;
import org.example.service.PostService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class MainServlet extends HttpServlet {

    private static final String API_POSTS = "/api/posts";
    private static final String API_POSTS_ID_REGEX = "/api/posts/\\d+";
    private static final String GET_METHOD = "GET";
    private static final String POST_METHOD = "POST";
    private static final String DELETE_METHOD = "DELETE";

    private PostController controller;
//    @Override
//    public void init() {
//        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        controller = context.getBean(PostController.class);
//    }
    @Override
    public void init() {
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();

            // primitive routing
            if (GET_METHOD.equals(method) && API_POSTS.equals(path)) {
                controller.all(resp);
                return;
            }
            if (GET_METHOD.equals(method) && path.matches(API_POSTS_ID_REGEX)) {
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                controller.getById(id, resp);
                return;
            }
            if (POST_METHOD.equals(method) && API_POSTS.equals(path)) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (DELETE_METHOD.equals(method) && path.matches(API_POSTS_ID_REGEX)) {
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}