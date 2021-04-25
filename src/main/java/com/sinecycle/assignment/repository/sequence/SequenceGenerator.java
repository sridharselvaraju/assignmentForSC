package com.sinecycle.assignment.repository.sequence;

import com.sinecycle.assignment.entity.AgentInfo;
import com.sinecycle.assignment.entity.CustomerInfo;
import com.sinecycle.assignment.entity.Ticket;
import com.sinecycle.assignment.entity.TicketResponse;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.relational.QualifiedName;
import org.hibernate.boot.model.relational.QualifiedNameParser;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SequenceGenerator extends SequenceStyleGenerator {

  static final Map<String, String> entitySequenceMap = new HashMap<>();

  static {
    entitySequenceMap.put(Ticket.class.getSimpleName(), "seq_ticket_id");
    entitySequenceMap.put(TicketResponse.class.getSimpleName(), "seq_ticket_response_id");
    entitySequenceMap.put(AgentInfo.class.getSimpleName(), "seq_agent_info_id");
    entitySequenceMap.put(CustomerInfo.class.getSimpleName(), "seq_customer_info_id");
  }

  protected QualifiedName determineSequenceName(Properties params, Dialect dialect, JdbcEnvironment jdbcEnv) {
    final String sequenceName = entitySequenceMap.get(params.getProperty(JPA_ENTITY_NAME));

    if (sequenceName.contains(".")) {
      return QualifiedNameParser.INSTANCE.parse(sequenceName);
    } else {
      final Identifier catalog = jdbcEnv.getIdentifierHelper().toIdentifier(ConfigurationHelper.getString(CATALOG, params));
      final Identifier schema = jdbcEnv.getIdentifierHelper().toIdentifier(ConfigurationHelper.getString(SCHEMA, params));
      return new QualifiedNameParser.NameParts(catalog, schema, jdbcEnv.getIdentifierHelper().toIdentifier(sequenceName));
    }
  }
}
