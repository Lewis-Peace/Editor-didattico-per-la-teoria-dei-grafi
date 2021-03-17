package Backend.Exceptions;

import Backend.Edge;

public class ConnectionDoesNotExistException {
    

    private Edge connection;

    public ConnectionDoesNotExistException(Edge connection) {
        this.connection = connection;
    }

    public String toString() {
        return "the connection " + connection.toString() + " doesn't exists";
    }
}
