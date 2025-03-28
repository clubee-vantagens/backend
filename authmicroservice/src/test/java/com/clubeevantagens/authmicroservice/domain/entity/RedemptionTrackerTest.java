package com.clubeevantagens.authmicroservice.domain.entity;

import com.clubeevantagens.authmicroservice.model.RedemptionTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class RedemptionTrackerTest {

  private RedemptionTracker redemptionTracker;

  @BeforeEach
  public void setUp() {
    redemptionTracker = new RedemptionTracker(0,0);
  }

  @Test
  @DisplayName("Should create a redemption tracker")
  public void testCreateRedemption() {
    assertThat(redemptionTracker.getTotalRedemptions()).isEqualTo(0);
    assertThat(redemptionTracker.getRedemptionsLast7Days()).isEqualTo(0);
  }

  @Test
  @DisplayName("Should restore a redemption tracker with the params obtained from the database ")
  public void testRestoreRedemption() {
    RedemptionTracker redemptionTrackerRestore = new RedemptionTracker(3, 2, LocalDateTime.now().minusDays(7));
    assertThat(redemptionTrackerRestore.getTotalRedemptions()).isEqualTo(3);
    assertThat(redemptionTrackerRestore.getRedemptionsLast7Days()).isEqualTo(0);
  }

  @Test
  @DisplayName("Should increment both total and 7-day counters when a redemption is registered")
  public void testRegisterRedemption() {
    redemptionTracker.registerRedemption();
    redemptionTracker.registerRedemption();
    assertThat(redemptionTracker.getTotalRedemptions()).isEqualTo(2);
    assertThat(redemptionTracker.getRedemptionsLast7Days()).isEqualTo(2);
  }

  @Test
  @DisplayName("Should not reset 7-day counter if less than 7 days have passed")
  public void testNoResetBefore7Days() throws Exception {
    redemptionTracker.registerRedemption();
    adjustCycleStartDaysAgo(6);
    assertThat(redemptionTracker.getTotalRedemptions()).isEqualTo(1);
    assertThat(redemptionTracker.getRedemptionsLast7Days()).isEqualTo(1);
  }

  @Test
  @DisplayName("Should reset 7-day counter exactly after 7 days")
  public void testResetAfter7Days() throws Exception {
    redemptionTracker.registerRedemption();
    adjustCycleStartDaysAgo(7);
    redemptionTracker.registerRedemption();
    assertThat(redemptionTracker.getTotalRedemptions()).isEqualTo(2);
    assertThat(redemptionTracker.getRedemptionsLast7Days()).isEqualTo(1);
  }

  @Test
  @DisplayName("Should reset 7-day counter if more than 7 days have passed")
  public void testResetAfterMoreThan7Days() throws Exception {
    redemptionTracker.registerRedemption();
    adjustCycleStartDaysAgo(10);
    redemptionTracker.registerRedemption();
    assertThat(redemptionTracker.getTotalRedemptions()).isEqualTo(2);
    assertThat(redemptionTracker.getRedemptionsLast7Days()).isEqualTo(1);
  }

  private void adjustCycleStartDaysAgo(int daysAgo) throws Exception {
    Field cycleStartField = RedemptionTracker.class.getDeclaredField("cycleStart");
    cycleStartField.setAccessible(true);
    LocalDateTime newCycleStart = LocalDateTime.now().minusDays(daysAgo);
    cycleStartField.set(redemptionTracker, newCycleStart);
  }
}
