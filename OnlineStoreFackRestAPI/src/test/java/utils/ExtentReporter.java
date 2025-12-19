package utils;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporter implements ITestListener {

    private static ExtentReports extent;
    private static ExtentSparkReporter sparkReporter;
    private static ThreadLocal<ExtentTest> testNode = new ThreadLocal<>();
    private static Map<String, ExtentTest> suiteMap = new ConcurrentHashMap<>();
    private static String reportPath;

    // ------------------ EXTENT INITIALIZATION ------------------
    private synchronized static ExtentReports getExtentInstance() {

        if (extent == null) {

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(new Date());

            // Create reports directory
            String reportsDir = System.getProperty("user.dir")
                    + File.separator + "reports";

            File dir = new File(reportsDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            reportPath = reportsDir + File.separator
                    + "ExtentReport_" + timeStamp + ".html";

            sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setDocumentTitle("API Automation Report");
            sparkReporter.config().setReportName("Execution Summary");
            sparkReporter.config().setTheme(Theme.STANDARD);

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("User", System.getProperty("user.name"));
            extent.setSystemInfo("OS", System.getProperty("os.name"));
        }
        return extent;
    }

    // ------------------ SUITE START ------------------
    @Override
    public void onStart(ITestContext context) {
        ExtentTest suiteNode = getExtentInstance()
                .createTest(context.getSuite().getName());

        suiteMap.put(context.getName(), suiteNode);
        suiteNode.info("Suite Started");
    }

    // ------------------ TEST START ------------------
    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest suiteNode = suiteMap.get(result.getTestContext().getName());
        ExtentTest test = suiteNode.createNode(result.getMethod().getMethodName());
        testNode.set(test);
    }

    // ------------------ TEST PASS ------------------
    @Override
    public void onTestSuccess(ITestResult result) {
        testNode.get().log(Status.PASS, "Test Passed");
        testNode.get().info("Execution Time: " + getExecutionTime(result) + " ms");
    }

    // ------------------ TEST FAIL ------------------
    @Override
    public void onTestFailure(ITestResult result) {
        testNode.get().log(Status.FAIL, "Test Failed");
        testNode.get().log(Status.FAIL, result.getThrowable());
        testNode.get().info("Execution Time: " + getExecutionTime(result) + " ms");
    }

    // ------------------ TEST SKIP ------------------
    @Override
    public void onTestSkipped(ITestResult result) {
        testNode.get().log(Status.SKIP, "Test Skipped");
        testNode.get().log(Status.SKIP, result.getThrowable());
    }

    // ------------------ SUITE FINISH ------------------
    @Override
    public void onFinish(ITestContext context) {

        ExtentTest suiteNode = suiteMap.get(context.getName());

        suiteNode.info("Suite Finished");
        suiteNode.info("Passed: " + context.getPassedTests().size());
        suiteNode.info("Failed: " + context.getFailedTests().size());
        suiteNode.info("Skipped: " + context.getSkippedTests().size());

        getExtentInstance().flush();

        System.out.println("Extent Report generated at:");
        System.out.println(reportPath);
    }

    // ------------------ EXECUTION TIME ------------------
    private long getExecutionTime(ITestResult result) {
        return result.getEndMillis() - result.getStartMillis();
    }
}
