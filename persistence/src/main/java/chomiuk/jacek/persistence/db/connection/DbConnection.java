package chomiuk.jacek.persistence.db.connection;

import org.jdbi.v3.core.Jdbi;

public class DbConnection {
    private final static DbConnection connection = new DbConnection();

    private final String url = "jdbc:mysql://localhost:3306/cinema_db?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final String username = "root";
    private final String password = "root";

    private final Jdbi jdbi = Jdbi.create(url, username, password);

    public DbConnection() {
        createTables();
    }

    public Jdbi getJdbi() {
        return jdbi;
    }
    public static DbConnection getInstance() {
        return connection;
    }

    @SuppressWarnings("SqlNoDataSourceInspection")
    private void createTables(){

        final String FILMS_SQL = """
                create table if not exists films(
                    id integer primary key auto_increment,
                    name varchar(50) not null,
                    genre_id integer not null, 
                    start_date date not null,
                    end_date date not null
                );
         """;

        final String CINEMAS_SQL = """
                create table if not exists cinemas(
                id integer primary key auto_increment,
                name varchar(50) not null,
                city_id integer not null
                );
         """;

        final String USERS_SQL = """
                create table if not exists users(
                id integer primary key auto_increment,
                name varchar(50) not null,
                username varchar(50) not null,
                password varchar(50) not null,
                email varchar(50) not null,
                status_id integer not null
                );
         """;

        final String CITIES_SQL = """
                create table if not exists cities(
                id integer primary key auto_increment,
                name varchar(50) not null
                );
         """;

        final String GENRES_SQL = """
                create table if not exists genres(
                id integer primary key auto_increment,
                name varchar(50) not null
                );
         """;

        final String CHAIRS_SQL = """
                create table if not exists chairs(
                id integer primary key auto_increment,
                row_num integer not null,
                place_number integer not null,
                cinema_room_id integer not null
                );
         """;

        final String DISCOUNTS_SQL = """
                create table if not exists discounts(
                id integer primary key auto_increment,
                percent integer not null
                );
         """;

        final String FAVOURITES_SQL = """
                create table if not exists favourites(
                id integer primary key auto_increment,
                film_id integer not null,
                user_id integer not null
                );
         """;

        final String ROOMS_SQL = """
                create table if not exists rooms(
                id integer primary key auto_increment,
                name varchar(50) not null,
                rows_number integer not null,
                places_number integer not null,
                cinema_id integer not null
                );
         """;

        final String SEATS_SQL = """
                create table if not exists seats(
                id integer primary key auto_increment,
                chair_id integer not null,
                show_id integer not null,
                state_id integer not null
                );
         """;

        final String SHOWS_SQL = """
                create table if not exists shows(
                id integer primary key auto_increment,
                film_id integer not null,
                cinema_room_id integer not null,
                show_time datetime not null
                );
         """;

        final String STATES_SQL = """
                create table if not exists states(
                id integer primary key auto_increment,
                name varchar(50) not null
                );
         """;


        final String STATUSES_SQL = """
                create table if not exists statuses(
                id integer primary key auto_increment,
                status_name varchar(50) not null
                );
         """;

        final String TICKETS_SQL = """
                create table if not exists tickets(
                id integer primary key auto_increment,
                show_id integer not null,
                type_id integer not null,
                price decimal(19,4) not null,
                chair_id integer not null
                );
         """;

        final String TYPES_SQL = """
                create table if not exists types(
                id integer primary key auto_increment,
                name varchar(50) not null,
                discount_id integer not null
                )
        """;

        final String RESERVATIONS_SQL = """
                create table if not exists reservations(
                id integer primary key auto_increment,
                show_id integer not null,
                type_id integer not null,
                chair_id integer not null
                )
        """;

        final String PRICES_SQL = """
                create table if not exists prices(
                id integer primary key auto_increment,
                name varchar(50) not null,
                value decimal(19,4) not null
                )
        """;

        final String RECOMMENDATIONS_SQL = """
                create table if not exists recommendations(
                id integer primary key auto_increment,
                film_id integer not null
                );
         """;

        jdbi.useHandle(handle -> {
            handle.execute(CHAIRS_SQL);
            handle.execute(CINEMAS_SQL);
            handle.execute(CITIES_SQL);
            handle.execute(FAVOURITES_SQL);
            handle.execute(FILMS_SQL);
            handle.execute(GENRES_SQL);
            handle.execute(ROOMS_SQL);
            handle.execute(SEATS_SQL);
            handle.execute(SHOWS_SQL);
            handle.execute(STATES_SQL);
            handle.execute(STATUSES_SQL);
            handle.execute(TICKETS_SQL);
            handle.execute(USERS_SQL);
            handle.execute(TYPES_SQL);
            handle.execute(RESERVATIONS_SQL);
            handle.execute(DISCOUNTS_SQL);
            handle.execute(PRICES_SQL);
            handle.execute(RECOMMENDATIONS_SQL);
        });
    }
}
