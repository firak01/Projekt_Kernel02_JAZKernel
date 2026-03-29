package base.common.datetime;
//package de.his.core.common.datetime;

//import static de.his.core.common.datetime.DateWithDayAccuracyUtil.countIntersectionDays;
import static base.common.datetime.DateWithDayAccuracyUtil.countIntersectionDays;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

//import de.his.core.util.ReduceFunction;
import base.util.ReduceFunction;

/**
 * Abstract class for date ranges.
 * <br />
 * Company: HIS
 * @author hoersch
 * @version $Revision: 1.5 $
 */
public abstract class DateRange {

    /**
     *
     * @return the start date of this range
     */
    public abstract Date getStartDate();

    /**
     *
     * @return the end date of this range
     */
    public abstract Date getEndDate();

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

    /**
     *
     * @param collection
     * @return a new collection containing only elements valid for this month or null if the given collection is null
     */
    public <RV extends RestrictedValidity> Collection<RV> filterByValidity(Collection<RV> collection) {
        if (collection == null) {
            return null;
        }
        List<RV> result = Lists.newArrayList();

        for (RV rv : collection) {
            if (isValidFor(rv)) {
                result.add(rv);
            }
        }
        return result;
    }

    /**
     *
     * @param restrictedvalidity
     * @return true if the restricted validity object is valid in this date range
     */
    public <RV extends RestrictedValidity> boolean isValidFor(RV restrictedvalidity) {
        return intersectionDays(restrictedvalidity) > 0;
    }

    /**
     *
     * @param restrictedvalidity
     * @return true if the restricted validity object is completly valid in this date range
     */
    public <RV extends RestrictedValidity> boolean isCompletelyValidFor(RV restrictedvalidity) {
        return intersectionDays(restrictedvalidity) == days();
    }

    /**
     *
     * @param collection
     * @return a new collection containing only elements valid for this month or null if the given collection is null
     */
    public <RV extends RestrictedValidity> Collection<RV> filterByExactValidity(Collection<RV> collection) {
        if (collection == null) {
            return null;
        }
        List<RV> result = Lists.newArrayList();
        Date startDate = getStartDate();
        Date endDate = getEndDate();
        for (RV rv : collection) {
            if (DateWithDayAccuracyUtil.isWithin(startDate, endDate, rv.getValidFrom(), rv.getValidTo())) {
                result.add(rv);
            }
        }
        return result;
    }

    /**
     * Gets the restricted validity object valid in this month. Uses the "latest" if there are more than one.
     * @param collection
     * @return the latest valid object
     */
    public <RV extends RestrictedValidity> RV getLatestValid(Collection<RV> collection) {
        if (collection == null) {
            return null;
        }
        Date date = getEndDate();
        for (RV restrictedValidity : collection) {
            if (DateWithDayAccuracyUtil.isIn(date, restrictedValidity.getValidFrom(), restrictedValidity.getValidTo())) {
                return restrictedValidity;
            }
        }
        date = getStartDate();
        for (RV restrictedValidity : collection) {
            if (DateWithDayAccuracyUtil.isIn(date, restrictedValidity.getValidFrom(), restrictedValidity.getValidTo())) {
                return restrictedValidity;
            }
        }
        return null;
    }

    /**
     * @return the days of of this range
     */
    public long days() {
        return DateWithDayAccuracyUtil.countDays(getStartDate(), getEndDate());
    }

    /**
     * @return the months of this date range
     */
    public Iterable<Month> months() {
        return Month.range(Month.of(getStartDate()), Month.of(getEndDate()));
    }

    /**
     *
     * @param validityObject
     * @return the days of intersection
     */
    public <RV extends RestrictedValidity> long intersectionDays(RV validityObject) {
        return countIntersectionDays(validityObject.getValidFrom(), validityObject.getValidTo(), getStartDate(), getEndDate());
    }

    /**
     *
     * @return a function to accumulate the intersected days with this mounth
     */
    public <RV extends RestrictedValidity> ReduceFunction<RV, Long> intersectionDays() {
        return new ReduceFunction<RV, Long>() {
            @Override
            public Long apply(Long accum, RV next) {
                return Long.valueOf(accum.intValue() + intersectionDays(next));
            }
        };
    }

    /**
     * @param range
     * @return the intersection of this and the given range
     */
    public DateRange intersect(DateRange range) {
        if (range == null) {
            return null;
        }
        if (countIntersectionDays(range.getStartDate(), range.getEndDate(), getStartDate(), getEndDate())<=0) {
            return null;
        }

        Date start = null;
        if (getStartDate().before(range.getStartDate())) {
            start = range.getStartDate();
        } else {
            start = getStartDate();
        }
        Date end = null;
        if (getEndDate().before(range.getEndDate())) {
            end = getEndDate();
        } else {
            end = range.getEndDate();
        }
        return new DayRange(start, end);
    }
}
