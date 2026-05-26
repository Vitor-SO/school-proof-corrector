package vitorcode.shared.interfaces;

import javax.sql.DataSource;

public interface ITenantDatasourceProvider {

    DataSource getDataSource(
            String databaseName,
            String user,
            String password
    );
}