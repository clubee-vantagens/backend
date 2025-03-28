package com.clubeevantagens.authmicroservice.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class RedemptionTracker {
  private int totalRedemptions;
  private int redemptionsLast7Days;
  private LocalDateTime cycleStart;

  public RedemptionTracker(int totalRedemptions, int redemptionsLast7Days) {
    this.totalRedemptions = totalRedemptions;
    this.redemptionsLast7Days = redemptionsLast7Days;
    this.cycleStart = LocalDateTime.now();
  }

  public RedemptionTracker(int totalRedemptions, int redemptionsLast7Days, LocalDateTime cycleStart) {
    this.totalRedemptions = totalRedemptions;
    this.redemptionsLast7Days = redemptionsLast7Days;
    this.cycleStart = cycleStart;
    checkReset();
  }

  public void registerRedemption() {
    checkReset();
    this.totalRedemptions++;
    this.redemptionsLast7Days++;
  }

  public int getTotalRedemptions() {
    return this.totalRedemptions;
  }

  public int getRedemptionsLast7Days() {
    return this.redemptionsLast7Days;
  }

  public LocalDateTime getCycleStart() {
    return this.cycleStart;
  }

  private void checkReset() {
    LocalDateTime now = LocalDateTime.now();
    Duration duration = Duration.between(this.cycleStart, now);
    if(duration.toDays() >= 7) {
      this.redemptionsLast7Days = 0;
      this.cycleStart = now;
    }
  }
}
