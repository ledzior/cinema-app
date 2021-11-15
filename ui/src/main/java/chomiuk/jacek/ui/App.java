package chomiuk.jacek.ui;

import chomiuk.jacek.persistence.db.connection.DbConnection;
import chomiuk.jacek.persistence.db.model.Film;
import chomiuk.jacek.persistence.db.model.Genre;
import chomiuk.jacek.persistence.db.model.Status;
import chomiuk.jacek.persistence.db.model.User;
import chomiuk.jacek.persistence.db.repository.*;
import chomiuk.jacek.persistence.db.repository.impl.*;
import chomiuk.jacek.service.service.AdministrationService;
import chomiuk.jacek.service.service.AuthorizationService;
import chomiuk.jacek.service.service.FilmService;
import chomiuk.jacek.service.service.TicketService;
import chomiuk.jacek.ui.menu.MenuService;
import chomiuk.jacek.ui.web.FilmsRouting;
import org.jdbi.v3.core.Jdbi;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.time.LocalDate;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        //Jdbi jdbi = DbConnection.getInstance().getJdbi();
        var context = new AnnotationConfigApplicationContext(AppSpringConfig.class);
        var filmService = context.getBean("filmService", FilmService.class);
        var ticketService = context.getBean("ticketService", TicketService.class);
        var administrationService = context.getBean("administrationService", AdministrationService.class);
        var authorizationService = context.getBean("authorizationService", AuthorizationService.class);
        var menuService = context.getBean("menuService", MenuService.class);

        menuService.authorizationMenu();

        //port(8080);
        //var filmsRouting = context.getBean("filmsRouting", FilmsRouting.class);
        //filmsRouting.initRoutes();
    }
}
