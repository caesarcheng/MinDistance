/*package cst.zju.com.util;
public static TimeSeriesCollection createTimeSeries(
		List<PressureBean> list, int dayOrweekOrmonth, Log log, String shou,String shu
		) {

	TimeSeriesCollection timesers = new TimeSeriesCollection();

	int mon = 1;
	int day = 1;
	int ye = 2000;
	int week = 1;

	// 按天显示
	if (dayOrweekOrmonth == 0) {

		TimeSeries timeseries = new TimeSeries(shou,
				org.jfree.data.time.Day.class);
		TimeSeries timeseries1 = new TimeSeries("c1",
				org.jfree.data.time.Day.class);

		TimeSeries timeseriedia = new TimeSeries(shu,
				org.jfree.data.time.Day.class);
		TimeSeries timeseriedia1 = new TimeSeries("d1",
				org.jfree.data.time.Day.class);

		Iterator<PressureBean> it = list.iterator();
		while (it.hasNext()) {
			PressureBean pres = it.next();
			String date = pres.getBpDate();

			ye = Integer.parseInt(date.substring(0, 4));
			mon = Integer.parseInt(date.substring(5, 7));
			day = Integer.parseInt(date.substring(8, date.length()));
			Day days = new Day(day, mon, ye);

			double sys = pres.getSyspress();
			double dia = pres.getDiapress();
			if (sys != -1 && sys > 0) {
				timeseries.add(days, sys);
			} else {
				timeseries1.add(days, null);
			}
			if (sys != -1 && sys > 0) {
				timeseriedia.add(days, dia);
			} else {
				timeseriedia1.add(days, null);
			}

		}

		timesers.addSeries(timeseries);
		timesers.addSeries(timeseriedia);
		timesers.addSeries(timeseries1);
		timesers.addSeries(timeseriedia1);

	} else if (dayOrweekOrmonth == 1) {//按周显示
		TimeSeries timeseries = new TimeSeries(shou,
				org.jfree.data.time.Week.class);
		TimeSeries timeseries1 = new TimeSeries("c1",
				org.jfree.data.time.Week.class);

		TimeSeries timeseriedia = new TimeSeries(shu,
				org.jfree.data.time.Week.class);
		TimeSeries timeseriedia1 = new TimeSeries("d1",
				org.jfree.data.time.Week.class);

		Iterator<PressureBean> it = list.iterator();
		while (it.hasNext()) {
			PressureBean pres = it.next();
			String date = pres.getBpDate();

			String[] spls = date.split("-");
			if (spls.length == 2) {
				ye = Integer.parseInt(spls[0]);
				mon = Integer.parseInt(spls[1]);
			} else {
				log.error("the date of weeks is wrong");
			}

			Week days = new Week(mon, ye);
			double sys = pres.getSyspress();
			double dia = pres.getDiapress();

			if (sys != -1 && sys > 0) {
				timeseries.add(days, sys);
			} else {
				timeseries1.add(days, null);
			}
			if (sys != -1 && sys > 0) {
				timeseriedia.add(days, dia);
			} else {
				timeseriedia1.add(days, null);
			}

		}

		timesers.addSeries(timeseries);
		timesers.addSeries(timeseriedia);
		timesers.addSeries(timeseries1);
		
		timesers.addSeries(timeseriedia1);

	} else {//按月显示
		TimeSeries timeseries = new TimeSeries(shou,
				org.jfree.data.time.Month.class);
		TimeSeries timeseries1 = new TimeSeries("c1",
				org.jfree.data.time.Month.class);

		TimeSeries timeseriedia = new TimeSeries(shu,
				org.jfree.data.time.Month.class);
		TimeSeries timeseriedia1 = new TimeSeries("s",
				org.jfree.data.time.Month.class);

		Iterator<PressureBean> it = list.iterator();
		while (it.hasNext()) {
			PressureBean pres = it.next();
			String date = pres.getBpDate();

			String[] spls = date.split("-");
			if (spls.length == 2) {
				ye = Integer.parseInt(spls[0]);
				mon = Integer.parseInt(spls[1]);
			} else {
				log.error("the date of weeks is wrong");
			}

			Month days = new Month(mon, ye);

			double sys = pres.getSyspress();
			double dia = pres.getDiapress();

			if (sys != -1 && sys > 0) {
				timeseries.add(days, sys);
			} else {
				timeseries1.add(days, null);
			}
			if (sys != -1 && sys > 0) {
				timeseriedia.add(days, dia);
			} else {
				timeseriedia1.add(days, null);
			}

		}
		timesers.addSeries(timeseries);
		timesers.addSeries(timeseriedia);
		timesers.addSeries(timeseries1);
		
		timesers.addSeries(timeseriedia1);

	}

	return timesers;
}*/