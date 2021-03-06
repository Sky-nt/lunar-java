package com.nlf.calendar.eightchar;

import com.nlf.calendar.Lunar;
import com.nlf.calendar.util.LunarUtil;

/**
 * 大运
 *
 * @author 6tail
 */
public class DaYun {
  /**
   * 开始年(含)
   */
  private int startYear;
  /**
   * 结束年(含)
   */
  private int endYear;
  /**
   * 开始年龄(含)
   */
  private int startAge;
  /**
   * 结束年龄(含)
   */
  private int endAge;
  /**
   * 序数，0-9
   */
  private int index;
  /**
   * 运
   */
  private Yun yun;
  private Lunar lunar;

  public DaYun(Yun yun, int index) {
    this.yun = yun;
    this.lunar = yun.getLunar();
    this.index = index;
    int birthYear = lunar.getSolar().getYear();
    int year = yun.getStartSolar().getYear();
    if (index < 1) {
      this.startYear = birthYear;
      this.startAge = 1;
      this.endYear = year - 1;
      this.endAge = year - birthYear;
    } else {
      int add = (index - 1) * 10;
      this.startYear = year + add;
      this.startAge = this.startYear - birthYear + 1;
      this.endYear = this.startYear + 9;
      this.endAge = this.startAge + 9;
    }
  }

  public int getStartYear() {
    return startYear;
  }

  public int getEndYear() {
    return endYear;
  }

  public int getStartAge() {
    return startAge;
  }

  public int getEndAge() {
    return endAge;
  }

  public int getIndex() {
    return index;
  }

  public Lunar getLunar() {
    return lunar;
  }

  /**
   * 获取干支
   *
   * @return 干支
   */
  public String getGanZhi() {
    if (index < 1) {
      return "";
    }
    int offset = LunarUtil.getJiaZiIndex(lunar.getMonthInGanZhiExact());
    offset += yun.isForward() ? index : -index;
    int size = LunarUtil.JIA_ZI.length;
    if (offset >= size) {
      offset -= size;
    }
    if (offset < 0) {
      offset += size;
    }
    return LunarUtil.JIA_ZI[offset];
  }

  /**
   * 获取流年
   *
   * @return 流年
   */
  public LiuNian[] getLiuNian() {
    int n = 10;
    if (index < 1) {
      n = endYear-startYear+1;
    }
    LiuNian[] l = new LiuNian[n];
    for (int i = 0; i < n; i++) {
      l[i] = new LiuNian(this, i);
    }
    return l;
  }

  /**
   * 获取小运
   *
   * @return 小运
   */
  public XiaoYun[] getXiaoYun() {
    int n = 10;
    if (index < 1) {
      n = endYear-startYear+1;
    }
    XiaoYun[] l = new XiaoYun[n];
    for (int i = 0; i < n; i++) {
      l[i] = new XiaoYun(this, i, yun.isForward());
    }
    return l;
  }
}
