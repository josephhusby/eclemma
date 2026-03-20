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

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.jacoco.core.analysis.ICounter;
import org.jacoco.core.analysis.ICoverageNode.ElementType;

import org.eclipse.eclemma.core.CoverageTools;
import org.eclipse.eclemma.internal.ui.EclEmmaUIPlugin;
import org.eclipse.eclemma.internal.ui.UIMessages;

/**
 * Internal converter to create textual representations for table cells.
 */
class CellTextConverter {

  private static final NumberFormat COVERAGE_VALUE = new DecimalFormat(
      UIMessages.CoverageView_columnCoverageValue);

  private static final NumberFormat COUNTER_VALUE = DecimalFormat
      .getIntegerInstance();

  private final ViewSettings settings;
  private final ILabelProvider workbenchLabelProvider;

  CellTextConverter(ViewSettings settings) {
    this.settings = settings;
    this.workbenchLabelProvider = new WorkbenchLabelProvider();
  }

  String getElementName(Object element) {
    String text = getSimpleTextForJavaElement(element);
    if (element instanceof IPackageFragmentRoot
        && ElementType.BUNDLE.equals(settings.getRootType())) {
      text += " - " //$NON-NLS-1$
          + getElementName(((IPackageFragmentRoot) element).getJavaProject());
    }
    return text;
  }

  private String getSimpleTextForJavaElement(Object element) {
    if (element instanceof IPackageFragmentRoot) {
      final IPackageFragmentRoot root = (IPackageFragmentRoot) element;
      // tweak label if the package fragment root is the project itself:
      if (root.getElementName().length() == 0) {
        element = root.getJavaProject();
      }
      // shorten JAR references
      try {
        if (root.getKind() == IPackageFragmentRoot.K_BINARY) {
          return root.getPath().lastSegment();
        }
      } catch (JavaModelException e) {
        EclEmmaUIPlugin.log(e);
      }
    }
    return workbenchLabelProvider.getText(element);
  }

  String getRatio(Object element) {
    return getRatio(element, settings.getCounters());
  }

  String getRatio(Object element,
      org.jacoco.core.analysis.ICoverageNode.CounterEntity entity) {
    ICounter counter = getCounter(element, entity);
    if (counter.getTotalCount() == 0) {
      return ""; //$NON-NLS-1$
    } else {
      return COVERAGE_VALUE.format(counter.getCoveredRatio());
    }
  }

  String getCovered(Object element) {
    return COUNTER_VALUE
        .format(getCounter(element, settings.getCounters()).getCoveredCount());
  }

  String getMissed(Object element) {
    return getMissed(element, settings.getCounters());
  }

  String getMissed(Object element,
      org.jacoco.core.analysis.ICoverageNode.CounterEntity entity) {
    return COUNTER_VALUE.format(getCounter(element, entity).getMissedCount());
  }

  String getTotal(Object element) {
    return getTotal(element, settings.getCounters());
  }

  String getTotal(Object element,
      org.jacoco.core.analysis.ICoverageNode.CounterEntity entity) {
    return COUNTER_VALUE.format(getCounter(element, entity).getTotalCount());
  }

  private ICounter getCounter(Object element,
      org.jacoco.core.analysis.ICoverageNode.CounterEntity entity) {
    return CoverageTools.getCoverageInfo(element).getCounter(entity);
  }

}
