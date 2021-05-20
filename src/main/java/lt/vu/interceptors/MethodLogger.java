package lt.vu.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

@Interceptor
@LoggedInvocation
    public class MethodLogger implements Serializable{

    private static int callCount = 0;
    @AroundInvoke
    public Object logMethodInvocation(InvocationContext context) throws Exception {
        System.out.println(callCount + " Called method: " + context.getMethod().getName());
        return context.proceed();
    }
}
