package database;

public enum DatabaseCredential {

    DB_CONNECTION("mysql"),
    DB_HOST(DatabaseEnv.host),
    DB_PORT(DatabaseEnv.port),
    DB_DATABASE(DatabaseEnv.database),
    DB_USERNAME(DatabaseEnv.username),
    DB_PASSWORD(DatabaseEnv.password);

    private String status;

    @Override
    public String toString() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    DatabaseCredential(String status) {
        this.status = status;
    }
}

