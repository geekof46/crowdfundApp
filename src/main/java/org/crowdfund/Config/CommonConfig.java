package org.crowdfund.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NonNull;
import org.crowdfund.dao.DBAccessor;
import org.crowdfund.models.db.User;
import org.crowdfund.utils.ModelConvertor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

/**
 * The type Common config.
 */
@Configuration
public class CommonConfig {

    /**
     * Gets object mapper.
     *
     * @return the object mapper
     */
    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule());
    }

    /**
     * Gets model convertor.
     *
     * @param mapper the mapper
     * @return the model convertor
     */
    @Bean
    public ModelConvertor getModelConvertor(@NonNull final ObjectMapper mapper) {
        return new ModelConvertor(mapper);
    }
}
