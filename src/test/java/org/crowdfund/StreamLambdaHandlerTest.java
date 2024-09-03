//package org.crowdfund;
//
//import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
//import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
//import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
//import com.amazonaws.services.lambda.runtime.Context;
//import org.crowdfund.StreamLambdaHandler;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.mock.http.MockInputStream;
//import org.springframework.mock.http.MockOutputStream;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class StreamLambdaHandlerTest {
//
//    @Mock
//    private SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
//
//    @Mock
//    private InputStream inputStream;
//
//    @Mock
//    private OutputStream outputStream;
//
//    @Mock
//    private Context context;
//
//    @Test
//    public void testHandleRequest() throws IOException {
//        // Arrange
//        when(handler.proxyStream(any(InputStream.class), any(OutputStream.class), any(Context.class))).thenAnswer(invocation -> {
//            // Mock the behavior of the handler.proxyStream method
//            return null;
//        });
//
//        // Act
//        StreamLambdaHandler streamLambdaHandler = new StreamLambdaHandler();
//        streamLambdaHandler.handleRequest(inputStream, outputStream, context);
//
//        // Assert
//        verify(handler).proxyStream(inputStream, outputStream, context);
//    }
//
//    @Test
//    public void testHandleRequest_ExceptionThrown() throws IOException {
//        // Arrange
//        when(handler.proxyStream(any(InputStream.class), any(OutputStream.class), any(Context.class))).thenThrow(new IOException("Test exception"));
//
//        // Act and Assert
//        try {
//            StreamLambdaHandler streamLambdaHandler = new StreamLambdaHandler();
//            streamLambdaHandler.handleRequest(inputStream, outputStream, context);
//        } catch (IOException e) {
//            assertEquals("Test exception", e.getMessage());
//        }
//    }
//}