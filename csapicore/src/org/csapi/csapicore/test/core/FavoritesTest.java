/*
 * Test class for Favorites.
 */
package org.csapi.csapicore.test.core;

import junit.framework.TestCase;

import org.csapi.csapicore.core.Favorites;
import org.csapi.csapicore.core.Report;

/**
 * @author grandpas
 */
public class FavoritesTest extends TestCase {

    /**
     * Simple class to test.
     */
    private Favorites simpleFavorites;

    /**
     * Constructors should not be tested: use setup instead.
     */
    protected final void setUp() {
        simpleFavorites = new Favorites();
        String[] attributes = new String[1];
        attributes[0] = "attributes";
        simpleFavorites.addFavoriteReport(new Report("query", attributes));
    }

    /**
     * Test method for 'org.csapi.csapicore.core.Favorites.
     * addFavoriteReport(Report)'.
     */
    public final void testFavoritesReport() {
        Report[] array = simpleFavorites.getArrayFavorites();
        assertEquals(1, array.length);
    }

    /**
     * Test method for 'org.csapi.csapicore.core.Favorites. getSize().
     */
    public final void testFavoritesSize() {
        assertEquals(1, simpleFavorites.getSize());
    }

    /**
     * Test method for 'org.csapi.csapicore.core.Favorites.clearFavorites()'.
     */
    public void testClearFavorites() {
        // todo implement me
    }

}
