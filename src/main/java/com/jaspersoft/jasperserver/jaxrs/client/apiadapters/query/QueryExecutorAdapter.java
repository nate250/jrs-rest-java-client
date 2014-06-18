/*
 * Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased  a commercial license agreement from Jaspersoft,
 * the following license terms  apply:
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of  the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public  License
 * along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
 */
package com.jaspersoft.jasperserver.jaxrs.client.apiadapters.query;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.AbstractAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.JerseyRequest;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.handling.DefaultErrorHandler;
import com.jaspersoft.jasperserver.jaxrs.client.core.operationresult.OperationResult;
import com.jaspersoft.jasperserver.jaxrs.client.dto.query.Query;
import com.jaspersoft.jasperserver.jaxrs.client.dto.query.QueryResult;

/**
 * This class is used for executing queries and retrieving a result of execution
 * as QueryResult entity.
 *
 * @author Krasnyanksiy.Alexander
 */
public class QueryExecutorAdapter extends AbstractAdapter {
    private final SessionStorage sessionStorage;
    private final StringBuilder uri;
    private final Query query;

    public QueryExecutorAdapter(SessionStorage sessionStorage, String uri, Query query) {
        super(sessionStorage);
        this.sessionStorage = sessionStorage;
        this.uri = new StringBuilder(uri);
        this.query = query;
    }

    /**
     * Support method for building Jersey request
     *
     * @return JerseyRequest instance
     */
    private JerseyRequest<QueryResult> buildRequest() {
        return JerseyRequest.buildRequest(
                sessionStorage,
                QueryResult.class,
                new String[]{
                        uri.insert(0, "/queryExecutor").toString()
                },
                new DefaultErrorHandler()
        );
    }

    public OperationResult<QueryResult> retrieveQueryResult() {
        JerseyRequest<QueryResult> req = buildRequest();
        req.setContentType("application/xml").setAccept("application/xml");
        return req.post(query);
    }
}