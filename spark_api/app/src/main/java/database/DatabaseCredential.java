package database;

public enum DatabaseCredential {
    DB_CONNECTION("mysql"),
    DB_HOST("hassankajila.com"),
    DB_PORT("3306"),
    DB_DATABASE("u601424401_on_web"),
    DB_USERNAME("u601424401_lordyhas"),
    DB_PASSWORD("lordyhas+5)0zTwDy*e");

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    DatabaseCredential(String status) {
        this.status = status;
    }
}

