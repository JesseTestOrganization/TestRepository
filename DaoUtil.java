package com.edianjucai.util;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;

public class DaoUtil {

    @SuppressWarnings("unchecked")
    public static <T> List<T> getVoListBySql(Session session, String sql, Class<T> voType, Map<String, Type> scalars, Object... parameters) {
        SQLQuery query = session.createSQLQuery(sql);
        
        if (scalars != null) {
            for (String column : scalars.keySet()) {
                query.addScalar(column, scalars.get(column));
            }
        }
        
        query.setResultTransformer(Transformers.aliasToBean(voType));
        
        for (int i = 0; parameters != null && i < parameters.length; i++) {
            query.setParameter(i, parameters[i]);
        }
        
        return query.list();
    }
    
}
