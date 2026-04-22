package dev.toys.Order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Sql("/toysTest.sql")
@AutoConfigureMockMvc
public class OrderControllerTest {

    private final MockMvcTester mockMvcTester;
    private final JdbcClient jdbcClient;

    public OrderControllerTest(MockMvcTester mockMvcTester, JdbcClient jdbcClient) {
        this.mockMvcTester = mockMvcTester;
        this.jdbcClient = jdbcClient;
    }

    private int idVanTestShippedOrder() {
        var sql = """
                select id
                from orders
                where status = 'SHIPPED'
                limit 1;
                """;

        return jdbcClient.sql(sql)
                .query(Integer.class)
                .single();
    }

    private int idVanTestProcessingOrder() {
        var sql = """
                select id
                from orders
                where status = 'PROCESSING'
                limit 1;
                """;

        return jdbcClient.sql(sql)
                .query(Integer.class)
                .single();
    }

    @Test
    @DisplayName("Unshipped orders opvragen geeft de juiste order id's terug, geen SHIPPED en geen CANCELLED.")
    void unshipperOrdersReturnsCorrectOrders() {

        var response = mockMvcTester
                .get()
                .uri("/orders/unshipped");

        assertThat(response)
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$[*].status")
                .isNotIn("SHIPPED", "CANCELLED");
    }

    @Test
    @DisplayName("Een order die niet gevonden wordt krijgt een NOT_FOUND status")
    void findOrderByIdNotFoundStatusCode() {

        var id = idVanTestShippedOrder();

        var response = mockMvcTester
                .post()
                .uri("/orders/{id}/shippings", Integer.MAX_VALUE);

        assertThat(response).hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Een order die al verzonden is krijgt een CONFLICT status")
    void findOrderByIdShippingsConflictStatusCode() {

        var id = idVanTestShippedOrder();

        var response = mockMvcTester
                .post()
                .uri("/orders/{id}/shippings", id);

        assertThat(response).hasStatus(HttpStatus.CONFLICT);
    }

    @Test
    @DisplayName("Set order as 'Shipped' if enough in stock, gives Status 200")
    void findOrderSetShipped() {

        var id = idVanTestProcessingOrder();

        var response = mockMvcTester
                .post()
                .uri("/orders/{id}/shippings", id);

        assertThat(response)
                .hasStatusOk(); //default
    }
}