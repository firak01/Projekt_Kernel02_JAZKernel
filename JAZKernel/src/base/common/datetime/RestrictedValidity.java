package base.common.datetime;
//package de.his.core.common.datetime;

import java.util.Date;

/**
 * Ein Gültigkeits-Zeitraum.
 * @author D.Scholz
 */
// Vorsicht be Refactorings: "RestrictedValidity.validFrom" und "RestrictedValidity.validTo" werden  als i18n-Schlüssel vorausgesetzt,
// und sind insgesamt im Projekt über 100x als Strings fest verdrahtet.
public interface RestrictedValidity {

    /**
     * @return validity.validFrom
     */
    public Date getValidFrom();

    /**
     * @return validity.validTo
     */
    public Date getValidTo();

    /**
     * @param validFrom the validFrom to set
     */
    public void setValidFrom(final Date validFrom);

    /**
     * @param validTo the validTo to set
     */
    public void setValidTo(final Date validTo);
}
