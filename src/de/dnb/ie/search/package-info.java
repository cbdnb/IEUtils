/**
 * Eine -zugegeben eigenwillige- Umsetzung des MVC-Musters.
 * 
 *  Das View enthält einige Panels (
 *  {@link de.dnb.ie.search.ComboPanel}, 
 *  {@link de.dnb.ie.search.CommandPanel}, 
 *  {@link de.dnb.ie.search.TextfieldPanel}), die sich alle von 
 *  {@link de.dnb.ie.search.GridPanel} abeleiten .
 *  
 *  Alle diese Panels implementieren {@link de.dnb.ie.search.IPanelModel} und
 *  sind damit auch Models, deren Werte gesetzt und abgefragt werden können.
 *  {@link de.dnb.ie.search.SearchModel} ist ein Container für diese 
 *  Teilmodels. Jedes Teilmodel formuliert seinen Teil der Suchfrage
 *  selbständig, {@link de.dnb.ie.search.SearchModel} setzt daraus die
 *  vollständige Suchfrage zusammen.
 *  
 *  {@link de.dnb.ie.search.SearchController}  erzeugt die einzelnen
 *  Model/View-Panels und versieht sie mit Listenern. Diese Listener
 *  verschalten die einzelnen Model/View-Panels mit 
 *  {@link de.dnb.ie.search.SearchModel}.
 */

package de.dnb.ie.search;

