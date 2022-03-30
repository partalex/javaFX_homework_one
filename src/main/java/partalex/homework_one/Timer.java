package partalex.homework_one;

import javafx.animation.AnimationTimer;

public class Timer extends AnimationTimer {
	private long time;
	private Timer.TimerSubscriber timerSubscriber;

	public Timer(Timer.TimerSubscriber timerSubscriber) {
		this.timerSubscriber = timerSubscriber;
	}

	public void handle(long now) {
		if (this.time != 0L) {
			long dns = now - this.time;
			this.timerSubscriber.update(dns);
		}

		this.time = now;
	}

	public interface TimerSubscriber {
		void update(long var1);
	}
}
