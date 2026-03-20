/*******************************************************************************
 * Copyright (c) 2006, 2020 Mountainminds GmbH & Co. KG and Contributors
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Marc R. Hoffmann - initial API and implementation
 *    Brock Janiczak - link with selection option (SF #1774547)
 *
 ******************************************************************************/
package org.eclipse.eclemma.internal.ui.coverageview;

import java.lang.annotation.ElementType;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.eclemma.internal.ui.UIMessages;

/**
 * All setting for the coverage view that will become persisted in the view's
 * memento.
 */
public class ViewSettings {

  private static final String KEY_SORTCOLUMN = "sortcolumn"; //$NON-NLS-1$
  private static final String KEY_REVERSESORT = "reversesort"; //$NON-NLS-1$
  private static final String KEY_COUNTERS = "counters"; //$NON-NLS-1$
  private static final String KEY_HIDEUNUSEDELEMENTS = "hideunusedelements"; //$NON-NLS-1$
  private static final String KEY_ROOTTYPE = "roottype"; //$NON-NLS-1$
  private static final String KEY_COLUMN0 = "column0"; //$NON-NLS-1$
  private static final String KEY_COLUMN1 = "column1"; //$NON-NLS-1$
  private static final String KEY_COLUMN2 = "column2"; //$NON-NLS-1$
  private static final String KEY_COLUMN3 = "column3"; //$NON-NLS-1$
  private static final String KEY_COLUMN4 = "column4"; //$NON-NLS-1$
  private static final String KEY_COLUMN5 = "column5"; //$NON-NLS-1$
  private static final String KEY_COLUMN6 = "column6"; //$NON-NLS-1$
  private static final String KEY_COLUMN7 = "column7"; //$NON-NLS-1$
  private static final String KEY_COLUMN8 = "column8"; //$NON-NLS-1$
  private static final String KEY_COLUMN9 = "column9"; //$NON-NLS-1$
  private static final String KEY_COLUMN10 = "column10"; //$NON-NLS-1$
  private static final String KEY_COLUMN11 = "column11"; //$NON-NLS-1$
  private static final String KEY_COLUMN12 = "column12"; //$NON-NLS-1$
  private static final String KEY_LINKED = "linked"; //$NON-NLS-1$
  public static final String KEY_ALL = "ALL"; //$NON-NLS-1$

  private static final Map<CounterEntity, String[]> COLUMNS_HEADERS = new HashMap<ICoverageNode.CounterEntity, String[]>();

  static {
    COLUMNS_HEADERS.put(CounterEntity.INSTRUCTION,
        new String[] { UIMessages.CoverageViewColumnElement_label,
            UIMessages.CoverageViewColumnCoverage_label,
            UIMessages.CoverageViewColumnCoveredInstructions_label,
            UIMessages.CoverageViewColumnMissedInstructions_label,
            UIMessages.CoverageViewColumnTotalInstructions_label });
    COLUMNS_HEADERS.put(CounterEntity.BRANCH,
        new String[] { UIMessages.CoverageViewColumnElement_label,
            UIMessages.CoverageViewColumnCoverage_label,
            UIMessages.CoverageViewColumnCoveredBranches_label,
            UIMessages.CoverageViewColumnMissedBranches_label,
            UIMessages.CoverageViewColumnTotalBranches_label });
    COLUMNS_HEADERS.put(CounterEntity.LINE,
        new String[] { UIMessages.CoverageViewColumnElement_label,
            UIMessages.CoverageViewColumnCoverage_label,
            UIMessages.CoverageViewColumnCoveredLines_label,
            UIMessages.CoverageViewColumnMissedLines_label,
            UIMessages.CoverageViewColumnTotalLines_label });
    COLUMNS_HEADERS.put(CounterEntity.METHOD,
        new String[] { UIMessages.CoverageViewColumnElement_label,
            UIMessages.CoverageViewColumnCoverage_label,
            UIMessages.CoverageViewColumnCoveredMethods_label,
            UIMessages.CoverageViewColumnMissedMethods_label,
            UIMessages.CoverageViewColumnTotalMethods_label });
    COLUMNS_HEADERS.put(CounterEntity.CLASS,
        new String[] { UIMessages.CoverageViewColumnElement_label,
            UIMessages.CoverageViewColumnCoverage_label,
            UIMessages.CoverageViewColumnCoveredTypes_label,
            UIMessages.CoverageViewColumnMissedTypes_label,
            UIMessages.CoverageViewColumnTotalTypes_label });
    COLUMNS_HEADERS.put(CounterEntity.COMPLEXITY,
        new String[] { UIMessages.CoverageViewColumnElement_label,
            UIMessages.CoverageViewColumnCoverage_label,
            UIMessages.CoverageViewColumnCoveredComplexity_label,
            UIMessages.CoverageViewColumnMissedComplexity_label,
            UIMessages.CoverageViewColumnTotalComplexity_label });
    COLUMNS_HEADERS.put(null,
        new String[] { UIMessages.CoverageViewColumnElement_label,
            UIMessages.CoverageViewColumnCoverageInstructions_label,
            UIMessages.CoverageViewColumnCoverageBranches_label,
            UIMessages.CoverageViewColumnMissedInstructions_label,
            UIMessages.CoverageViewColumnMissedBranches_label,
            UIMessages.CoverageViewColumnMissedComplexity_label,
            UIMessages.CoverageViewColumnMissedLines_label,
            UIMessages.CoverageViewColumnMissedMethods_label,
            UIMessages.CoverageViewColumnMissedTypes_label,
            UIMessages.CoverageViewColumnTotalComplexity_label,
            UIMessages.CoverageViewColumnTotalLines_label,
            UIMessages.CoverageViewColumnTotalMethods_label,
            UIMessages.CoverageViewColumnTotalTypes_label });
  }

  private static final int[] DEFAULT_COLUMNWIDTH = new int[] { 300, 120, 120,
      120, 120, 120, 120, 120, 120, 120, 120, 120, 120 };

  private int sortcolumn;
  private boolean reversesort;
  private CounterEntity counters;
  private ElementType roottype;
  private boolean hideunusedelements;
  private int[] columnwidths = new int[13];
  private boolean linked;

  public int getSortColumn() {
    return sortcolumn;
  }

  public boolean isReverseSort() {
    return reversesort;
  }

  public void toggleSortColumn(int column) {
    if (sortcolumn == column) {
      reversesort = !reversesort;
    } else {
      reversesort = false;
      sortcolumn = column;
    }
  }

  public String[] getColumnHeaders() {
    return COLUMNS_HEADERS.get(counters);
  }

  public CounterEntity getCounters() {
    return counters;
  }

  public void setCounters(CounterEntity counters) {
    this.counters = counters;
  }

  public ElementType getRootType() {
    return roottype;
  }

  public void setRootType(ElementType roottype) {
    this.roottype = roottype;
  }

  public boolean getHideUnusedElements() {
    return hideunusedelements;
  }

  public void setHideUnusedElements(boolean flag) {
    hideunusedelements = flag;
  }

  public void storeColumnWidth(TreeViewer viewer) {
    final TreeColumn[] columns = viewer.getTree().getColumns();
    for (int i = 0; i < columnwidths.length; i++) {
      columnwidths[i] = columns[i].getWidth();
    }
  }

  public void restoreColumnWidth(TreeViewer viewer) {
    final TreeColumn[] columns = viewer.getTree().getColumns();
    for (int i = 0; i < columnwidths.length; i++) {
      columns[i].setWidth(columnwidths[i]);
    }
  }

  public void updateColumnHeaders(TreeViewer viewer) {
    final String[] headers = COLUMNS_HEADERS.get(counters);
    final TreeColumn[] columns = viewer.getTree().getColumns();
    for (int i = 0; i < headers.length; i++) {
      columns[i].setText(headers[i]);
    }
  }

  public boolean isLinked() {
    return linked;
  }

  public void setLinked(boolean linked) {
    this.linked = linked;
  }

  public void init(IMemento memento) {
    sortcolumn = getInt(memento, KEY_SORTCOLUMN, CoverageView.COLUMN_MISSED);
    reversesort = getBoolean(memento, KEY_REVERSESORT, true);
    counters = getEnum(memento, KEY_COUNTERS, CounterEntity.class,
        CounterEntity.INSTRUCTION);
    roottype = getEnum(memento, KEY_ROOTTYPE, ElementType.class,
        ElementType.GROUP);
    hideunusedelements = getBoolean(memento, KEY_HIDEUNUSEDELEMENTS, false);
    columnwidths[0] = getWidth(memento, KEY_COLUMN0, DEFAULT_COLUMNWIDTH[0]);
    columnwidths[1] = getWidth(memento, KEY_COLUMN1, DEFAULT_COLUMNWIDTH[1]);
    columnwidths[2] = getWidth(memento, KEY_COLUMN2, DEFAULT_COLUMNWIDTH[2]);
    columnwidths[3] = getWidth(memento, KEY_COLUMN3, DEFAULT_COLUMNWIDTH[3]);
    columnwidths[4] = getWidth(memento, KEY_COLUMN4, DEFAULT_COLUMNWIDTH[4]);
    columnwidths[5] = getWidth(memento, KEY_COLUMN5, DEFAULT_COLUMNWIDTH[5]);
    columnwidths[6] = getWidth(memento, KEY_COLUMN6, DEFAULT_COLUMNWIDTH[6]);
    columnwidths[7] = getWidth(memento, KEY_COLUMN7, DEFAULT_COLUMNWIDTH[7]);
    columnwidths[8] = getWidth(memento, KEY_COLUMN8, DEFAULT_COLUMNWIDTH[8]);
    columnwidths[9] = getWidth(memento, KEY_COLUMN9, DEFAULT_COLUMNWIDTH[9]);
    columnwidths[10] = getWidth(memento, KEY_COLUMN10, DEFAULT_COLUMNWIDTH[10]);
    columnwidths[11] = getWidth(memento, KEY_COLUMN11, DEFAULT_COLUMNWIDTH[11]);
    columnwidths[12] = getWidth(memento, KEY_COLUMN12, DEFAULT_COLUMNWIDTH[12]);
    linked = getBoolean(memento, KEY_LINKED, false);
  }

  public void save(IMemento memento) {
    memento.putInteger(KEY_SORTCOLUMN, sortcolumn);
    memento.putBoolean(KEY_REVERSESORT, reversesort);
    if (counters == null) {
      memento.putString(KEY_COUNTERS, KEY_ALL);
    } else {
      memento.putString(KEY_COUNTERS, counters.name());
    }
    memento.putString(KEY_ROOTTYPE, roottype.name());
    memento.putBoolean(KEY_HIDEUNUSEDELEMENTS, hideunusedelements);
    memento.putInteger(KEY_COLUMN0, columnwidths[0]);
    memento.putInteger(KEY_COLUMN1, columnwidths[1]);
    memento.putInteger(KEY_COLUMN2, columnwidths[2]);
    memento.putInteger(KEY_COLUMN3, columnwidths[3]);
    memento.putInteger(KEY_COLUMN4, columnwidths[4]);
    memento.putInteger(KEY_COLUMN5, columnwidths[5]);
    memento.putInteger(KEY_COLUMN6, columnwidths[6]);
    memento.putInteger(KEY_COLUMN7, columnwidths[7]);
    memento.putInteger(KEY_COLUMN8, columnwidths[8]);
    memento.putInteger(KEY_COLUMN9, columnwidths[9]);
    memento.putInteger(KEY_COLUMN10, columnwidths[10]);
    memento.putInteger(KEY_COLUMN11, columnwidths[11]);
    memento.putInteger(KEY_COLUMN12, columnwidths[12]);
    memento.putBoolean(KEY_LINKED, linked);
  }

  private int getWidth(IMemento memento, String key, int preset) {
    final int w = getInt(memento, key, preset);
    return w == 0 ? preset : w;
  }

  private int getInt(IMemento memento, String key, int preset) {
    if (memento == null) {
      return preset;
    } else {
      Integer i = memento.getInteger(key);
      return i == null ? preset : i.intValue();
    }
  }

  private boolean getBoolean(IMemento memento, String key, boolean preset) {
    if (memento == null) {
      return preset;
    } else {
      Boolean b = memento.getBoolean(key);
      return b == null ? preset : b.booleanValue();
    }
  }

  private <T extends Enum<T>> T getEnum(IMemento memento, String key,
      Class<T> type, T preset) {
    if (memento == null) {
      return preset;
    }
    final String s = memento.getString(key);
    if (s == null) {
      return preset;
    }
    if (KEY_ALL.equals(s)) {
      return null;
    }
    try {
      return Enum.valueOf(type, s);
    } catch (IllegalArgumentException e) {
      return preset;
    }
  }

}
