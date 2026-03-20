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
 *
 ******************************************************************************/
package org.eclipse.eclemma.internal.ui.coverageview;

import org.eclipse.jdt.ui.JavaElementComparator;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.TreeColumn;
import org.jacoco.core.analysis.ICounter;
import org.jacoco.core.analysis.ICoverageNode.CounterEntity;

import org.eclipse.eclemma.core.CoverageTools;

/**
 * Internal sorter for the coverage view.
 */
class CoverageViewSorter extends ViewerComparator {

  private final ViewSettings settings;
  private final CoverageView view;
  private final ViewerComparator elementsorter = new JavaElementComparator();

  public CoverageViewSorter(ViewSettings settings, CoverageView view) {
    this.settings = settings;
    this.view = view;
  }

  void addColumn(final TreeViewerColumn viewerColumn, final int columnidx) {
    final TreeColumn column = viewerColumn.getColumn();
    if (settings.getSortColumn() == columnidx) {
      setSortColumnAndDirection(column, settings.isReverseSort());
    }
    column.addSelectionListener(new SelectionListener() {
      public void widgetSelected(SelectionEvent e) {
        settings.toggleSortColumn(columnidx);
        setSortColumnAndDirection(column, settings.isReverseSort());
        view.refreshViewer();
      }

      public void widgetDefaultSelected(SelectionEvent e) {
      }
    });
  }

  private void setSortColumnAndDirection(TreeColumn sortColumn,
      boolean reverse) {
    sortColumn.getParent().setSortColumn(sortColumn);
    sortColumn.getParent().setSortDirection(reverse ? SWT.DOWN : SWT.UP);
  }

  @Override
  public int compare(Viewer viewer, Object e1, Object e2) {
    CounterEntity counters = settings.getCounters();
    int sortCol = settings.getSortColumn();
    int res = 0;
    if (sortCol == CoverageView.COLUMN_ELEMENT) {
      res = elementsorter.compare(viewer, e1, e2);
    } else if (counters != null) {
      ICounter c1 = CoverageTools.getCoverageInfo(e1).getCounter(counters);
      ICounter c2 = CoverageTools.getCoverageInfo(e2).getCounter(counters);
      switch (sortCol) {
      case CoverageView.COLUMN_RATIO:
        res = Double.compare(c1.getCoveredRatio(), c2.getCoveredRatio());
        break;
      case CoverageView.COLUMN_COVERED:
        res = (int) (c1.getCoveredCount() - c2.getCoveredCount());
        break;
      case CoverageView.COLUMN_MISSED:
        res = (int) (c1.getMissedCount() - c2.getMissedCount());
        break;
      case CoverageView.COLUMN_TOTAL:
        res = (int) (c1.getTotalCount() - c2.getTotalCount());
        break;
      }
    } else {
      res = compareAllMode(e1, e2, sortCol);
    }

    if (res == 0) {
      res = elementsorter.compare(viewer, e1, e2);
    } else {
      res = settings.isReverseSort() ? -res : res;
    }
    return res;
  }

  private int compareAllMode(Object e1, Object e2, int sortCol) {
    CounterEntity entity = null;
    int type = -1; // 0=missed, 1=ratio, 2=total
    switch (sortCol) {
    case 1:
      entity = CounterEntity.INSTRUCTION;
      type = 1;
      break;
    case 2:
      entity = CounterEntity.BRANCH;
      type = 1;
      break;
    case 3:
      entity = CounterEntity.INSTRUCTION;
      type = 0;
      break;
    case 4:
      entity = CounterEntity.BRANCH;
      type = 0;
      break;
    case 5:
      entity = CounterEntity.COMPLEXITY;
      type = 0;
      break;
    case 6:
      entity = CounterEntity.LINE;
      type = 0;
      break;
    case 7:
      entity = CounterEntity.METHOD;
      type = 0;
      break;
    case 8:
      entity = CounterEntity.CLASS;
      type = 0;
      break;
    case 9:
      entity = CounterEntity.COMPLEXITY;
      type = 2;
      break;
    case 10:
      entity = CounterEntity.LINE;
      type = 2;
      break;
    case 11:
      entity = CounterEntity.METHOD;
      type = 2;
      break;
    case 12:
      entity = CounterEntity.CLASS;
      type = 2;
      break;
    default:
      return 0;
    }
    ICounter c1 = CoverageTools.getCoverageInfo(e1).getCounter(entity);
    ICounter c2 = CoverageTools.getCoverageInfo(e2).getCounter(entity);
    switch (type) {
    case 0:
      return (int) (c1.getMissedCount() - c2.getMissedCount());
    case 1:
      return Double.compare(c1.getCoveredRatio(), c2.getCoveredRatio());
    case 2:
      return (int) (c1.getTotalCount() - c2.getTotalCount());
    default:
      return 0;
    }
  }

}
