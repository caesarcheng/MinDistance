/*package cst.zju.com.util;

private static JFreeChart createChartPress(XYDataset xydataset,
		int weekOrmonth, String title, String y, String index, String week,
		String year, int searchby, String month, String nodatamess,
		List list, Log log, String bp_shou, String bp_shuzhang) {

	// 有可能用户在后面的版本中故意输入不正常数值，但是为了保证图片画图的完整，这里先计算
	// 用户血压值的最大值。


	double maxpress = 0;
	double addmax = 50;
	double min = 40;

	if (list != null && list.size() > 0) {
		Iterator<PressureBean> it = list.iterator();
		while (it.hasNext()) {
			PressureBean pres = it.next();
			double sys = pres.getSyspress();
			double dia = pres.getDiapress();

			if (maxpress < sys) {
				maxpress = sys;

			}

			if (maxpress < dia)
				maxpress = dia;

			if (min > sys) {
				min = sys;
			}

			if (min > dia)
				min = dia;

		}

		maxpress += addmax;
		min -= 10;


		log.info("high press value is =" + maxpress);

	}
	
	if (xydataset != null) {
		int counts = xydataset.getItemCount(0);
		if (counts == 0) {
			xydataset = null;
		}
	}

	JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(title, "",
			y, xydataset, true, true, false);
	jfreechart.setBackgroundPaint(Color.white);
	

	// 设置标题的颜色
	TextTitle text = new TextTitle(title);
	text.setPaint(new Color(102, 102, 102));
	jfreechart.setTitle(text);
	XYPlot xyplot = jfreechart.getXYPlot();
	xyplot.setBackgroundPaint(new Color(255, 253, 246));
	xyplot.setOutlineStroke(new BasicStroke(1.5f)); // 边框粗细
	ValueAxis vaxis = xyplot.getDomainAxis();
	vaxis.setAxisLineStroke(new BasicStroke(1.5f)); // 坐标轴粗细
	vaxis.setAxisLinePaint(new Color(215, 215, 215)); // 坐标轴颜色
	xyplot.setOutlineStroke(new BasicStroke(1.5f)); // 边框粗细
	vaxis.setLabelPaint(new Color(10, 10, 10)); // 坐标轴标题颜色
	vaxis.setTickLabelPaint(new Color(102, 102, 102)); // 坐标轴标尺值颜色
	vaxis.setLowerMargin(0.06d);// 分类轴下（左）边距
	vaxis.setUpperMargin(0.14d);// 分类轴下（右）边距,防止最后边的一个数据靠近了坐标轴。
	
	//X轴为日期格式，这里是专门的处理日期的类，
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
	if (weekOrmonth == 0) {//以天为刻度，时间格式为yyyy-MM-dd,如2008-02-06
		dateaxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 1, format));
	} else if (weekOrmonth == 1) {//以周为刻度，时间显示为 2009年第4周（（这里是SimpleDateFormat的用法，
		//这里为了作繁体版，英文版和简体版，用了国际化处理，将这些可变的资源在文字资源里面，注意一下，这里的y，M、w是SimpleDateFormat的关键字，
		//如英文表示09年第3周就是09W3，那么，这里的W需要用‘’引起来）
		format = new SimpleDateFormat("yyyy" + year + index + "w" + week);
		dateaxis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 7, format));
	} else if (weekOrmonth == 2) {//以月为刻度，时间显示为09-02 （09年2月）
		format = new SimpleDateFormat("yy-MM");
		dateaxis
				.setTickUnit(new DateTickUnit(DateTickUnit.MONTH, 1, format));

	}
	dateaxis.setVerticalTickLabels(false); // 设为true表示横坐标旋转到垂直。
	if (searchby == 6 || searchby == 3) {
		dateaxis.setAutoTickUnitSelection(true); // 由于横轴标签过多，这里设置为自动格式 。
		dateaxis.setDateFormatOverride(format);
	}
	dateaxis.setTickMarkPosition(DateTickMarkPosition.START);

	ValueAxis valueAxis = xyplot.getRangeAxis();
	valueAxis.setUpperBound(maxpress);
	valueAxis.setAutoRangeMinimumSize(1);
	valueAxis.setLowerBound(min);
	valueAxis.setAutoRange(false);

	valueAxis.setAxisLineStroke(new BasicStroke(1.5f)); // 坐标轴粗细
	valueAxis.setAxisLinePaint(new Color(215, 215, 215)); // 坐标轴颜色
	valueAxis.setLabelPaint(new Color(10, 10, 10)); // 坐标轴标题颜色
	valueAxis.setTickLabelPaint(new Color(102, 102, 102)); // 坐标轴标尺值颜色
	
	xyplot.setRangeGridlinesVisible(true);
	xyplot.setDomainGridlinesVisible(true);
	xyplot.setRangeGridlinePaint(Color.LIGHT_GRAY);
	xyplot.setDomainGridlinePaint(Color.LIGHT_GRAY);
	xyplot.setBackgroundPaint(new Color(255, 253, 246));
	xyplot.setNoDataMessage(nodatamess);//没有数据时显示的文字说明。
	xyplot.setNoDataMessageFont(new Font("", Font.BOLD, 14));//字体的大小，粗体。
	xyplot.setNoDataMessagePaint(new Color(87, 149, 117));//字体颜色
	xyplot.setAxisOffset(new RectangleInsets(0d, 0d, 0d, 5d)); //

	// add range marker(舒张压的区域marker,范围是从62到81)

	double lowpress = 62;
	double uperpress = 81;
	IntervalMarker intermarker = new IntervalMarker(lowpress, uperpress);
	intermarker.setPaint(Color.decode("#66FFCC"));// 域顏色
	
	intermarker.setLabelFont(new Font("SansSerif", 41, 14));
	intermarker.setLabelPaint(Color.RED);
	intermarker.setLabel(bp_shuzhang);

	if (xydataset != null) {
		xyplot.addRangeMarker(intermarker, Layer.BACKGROUND);
	}
//(收缩压的区域marker，范围是从102到120)
	double lowpress1 = 102;
	double uperpress1 = 120;
	IntervalMarker inter = new IntervalMarker(lowpress1, uperpress1);
	inter.setLabelOffsetType(LengthAdjustmentType.EXPAND);
	inter.setPaint(Color.decode("#66FFCC"));// 域顏色


	inter.setLabelFont(new Font("SansSerif", 41, 14));
	inter.setLabelPaint(Color.RED);
	inter.setLabel(bp_shou);
	
	if (xydataset != null) {
		xyplot.addRangeMarker(inter, Layer.BACKGROUND); // 加上Layer.BACKGROUND，将maker调到折线下面。
	}

	XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyplot
			.getRenderer();
	//第一条折线的颜色
	xylineandshaperenderer.setBaseItemLabelsVisible(true);
	xylineandshaperenderer.setSeriesFillPaint(0, new Color(127, 128, 0));
	xylineandshaperenderer.setSeriesPaint(0, new Color(127, 128, 0));

	xylineandshaperenderer.setSeriesShapesVisible(0, true);
	xylineandshaperenderer.setSeriesShapesVisible(1, true);

	//第二条折线的颜色
	xylineandshaperenderer.setSeriesFillPaint(1, new Color(254, 103, 0));
	xylineandshaperenderer.setSeriesPaint(1, new Color(254, 103, 0));
	xylineandshaperenderer.setSeriesShapesVisible(1, true);
	xylineandshaperenderer.setSeriesVisible(2, false);//
	xylineandshaperenderer.setSeriesVisible(3, false);//不显示下面标题

	//折线的粗细调
	StandardXYToolTipGenerator xytool = new StandardXYToolTipGenerator();
	xylineandshaperenderer.setToolTipGenerator(xytool);
	xylineandshaperenderer.setStroke(new BasicStroke(1.5f));

	// 显示节点的值
	xylineandshaperenderer.setBaseItemLabelsVisible(true);
	xylineandshaperenderer
			.setBasePositiveItemLabelPosition(new ItemLabelPosition(
					ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
	xylineandshaperenderer
			.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
	xylineandshaperenderer.setBaseItemLabelPaint(new Color(102, 102, 102));// 显示折点数值字体的颜色

	return jfreechart;
}*/