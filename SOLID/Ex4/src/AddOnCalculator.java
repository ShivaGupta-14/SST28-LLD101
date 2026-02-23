public class AddOnCalculator extends MoneyCalculator {

	@Override
	public Money calculate(BookingRequest req) {
		double add = 0.0;
		for (AddOn a : req.addOns) {
			if (a == AddOn.MESS)
				add += 1000.0;
			else if (a == AddOn.LAUNDRY)
				add += 500.0;
			else if (a == AddOn.GYM)
				add += 300.0;
		}

		return new Money(add);
	}
}
