package ru.skkdc.lis.conclusion.labinfo.impl;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import ru.skkdc.lis.conclusion.labinfo.ResultInfo;

public final class ResultInfoImpl implements ResultInfo {

	public static class Builder {
		private final DecimalFormat decFormatter;

		private String sVal;
		private double dVal = Double.NaN;
		private double lnorm = Double.NaN;
		private double unorm = Double.NaN;
		private String units;
		private String testName;

		public Builder() {
			super();
			DecimalFormatSymbols symbol = new DecimalFormatSymbols();
			symbol.setGroupingSeparator(' ');
			symbol.setDecimalSeparator('.');
			decFormatter = new DecimalFormat("##########.####################", symbol);
		}

		public Builder setVal(double val) {
			this.sVal = decFormatter.format(val);
			this.dVal = val;
			return this;
		}

		public Builder setVal(String val) {
			this.sVal = val;
			try {
				this.dVal = Double.parseDouble(val);			
			} catch (Exception e) {
				this.dVal = Double.NaN;
			}
			return this;
		}

		public Builder setLnorm(double lnorm) {
			this.lnorm = lnorm;
			return this;
		}

		public Builder setUnorm(double unorm) {
			this.unorm = unorm;
			return this;
		}

		public Builder setLnorm(String lnorm) {
			try {
				this.lnorm = Double.parseDouble(lnorm);
				return this;
			} catch (Exception e) {
				throw new IllegalArgumentException("lnorm must be a numeric");
			}
		}

		public Builder setUnorm(String unorm) {
			try {
				this.unorm = Double.parseDouble(unorm);
				return this;
			} catch (Exception e) {
				throw new IllegalArgumentException("unorm must be a numeric");
			}
		}

		public Builder setUnits(String units) {
			this.units = units;
			return this;
		}

		public Builder setTestName(String testName) {
			this.testName = testName;
			return this;
		}

		public ResultInfoImpl build() {
			ResultInfoImpl r = new ResultInfoImpl();

			if (testName == null)
				throw new IllegalArgumentException("testName can't be a null");
			if (sVal == null)
				throw new IllegalArgumentException("val can't be a null");

			if (!Double.isNaN(dVal)) {

				if (Double.isNaN(lnorm))
					throw new IllegalArgumentException("lnorm is required parameter");
				if (Double.isNaN(unorm))
					throw new IllegalArgumentException("unorm is required parameter");

				r.flag = ((dVal < lnorm || dVal > unorm) ? "!" : " ");
				r.unorm = decFormatter.format(unorm);
				r.lnorm = decFormatter.format(lnorm);
				r.val=decFormatter.format(dVal);

			} else {

				if (!Double.isNaN(lnorm))
					r.unorm = decFormatter.format(unorm);
				if (!Double.isNaN(unorm))
					r.lnorm = decFormatter.format(lnorm);
				r.val = sVal;
			}

			
			r.units = units;
			r.testName = testName;

			return r;
		}

	}

	private String val;
	private String lnorm;
	private String unorm;
	private String units;
	private String flag;
	private String testName;

	private ResultInfoImpl() {
		super();
	}

	@Override
	public String getValue() {
		return val;
	}

	@Override
	public String getLNorm() {
		return lnorm;
	}

	@Override
	public String getUNorm() {
		return unorm;
	}

	@Override
	public String getUnits() {
		return units;
	}

	@Override
	public String getFlag() {
		return flag;
	}

	@Override
	public String getTestName() {
		return testName;
	}

}