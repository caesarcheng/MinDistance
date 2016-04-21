/*package cst.zju.com.util;
public static void drawPressLineChart(IrisIoInterface io, Log log,
		TimeSeriesCollection timesers, int weekormonth, String title,
		String y, String index, String week, String year, int searchby,
		String month, String nodatamess, List list, String bp_shou,
		String bp_shuzhang) {

	JFreeChart chart = createChartPress(timesers, weekormonth, title, y,
			index, week, year, searchby, month, nodatamess, list, log,
			bp_shou, bp_shuzhang);

	HttpServletRequest request = io.getRequest();
	String filename = "";
	String graphURL = "";
	try {
		filename = ServletUtilities.saveChartAsPNG(chart, 650, 280, null,
				io.getSession());
		graphURL = request.getContextPath() + "/displayChart?filename="
				+ filename;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		log.error(e);
	}

	io.setData("filename1", filename, BeanShare.BEAN_SHARE_REQUEST);
	io.setData("presslineurl", graphURL, BeanShare.BEAN_SHARE_REQUEST);

}*/