package myjavafx;

import java.util.Collection;
import javafx.scene.control.ComboBox;

/**
 * ComboBox improved.
 * @author Johan
 * @param <T> ComboBox's items type
 * @see javafx.scene.control.ComboBox
 */
public class MyComboBox<T> extends ComboBox<T> {
    /**
     * Creates a default ComboBox instance with an empty items list and default selection model.
     */
    public MyComboBox() {
        super();
    }
    
    /**
     * Returns the currently selected object (which resides in the selected index position). 
     * If there are multiple items selected, this will return the object contained 
     * at the index returned by getSelectedIndex()
     * (which is always the index to the most recently selected item).
     * @return the currently selected object
     */
    public T getSelectedItem() {
        return getSelectionModel().getSelectedItem();
    }
    
    /**
     * Returns the integer value indicating the currently selected index in this model. 
     * If there are multiple items selected, this will return the most recent selection made.
     * @return the integer value indicating the currently selected index
     */
    public int getSelectedIndex() {
        return getSelectionModel().getSelectedIndex();
    }
    
    /**
     * Selects the index for the first instance of given object in the underlying data model. 
     * Since the SingleSelectionModel can only support having a single index selected at a time, 
     * this also causes any previously selected index to be unselected.
     * @param item new item to be selected
     */
    public void setSelectedItem(T item) {
        this.getSelectionModel().select(item);
    }
    
    /**
     * Select the given index.
     * @param index index to select
     */
    public void setSelectedIndex(int index) {
        getSelectionModel().select(index);
    }
    
    /**
     * In the SingleSelectionModel, this method is functionally equivalent to 
     * calling select(index), as only one selection is allowed at a time.
     * @param index index to select
     */
    public void clearAndSelect(int index) {
        getSelectionModel().clearAndSelect(index);
    }
    
    /**
     * Clears the selection model of all selected indices.
     */
    public void clearSelection() {
       getSelectionModel().clearSelection();
    }
    
    /**
     * Clears the selection of the given index, if it is currently selected.
     * @param index the index to be unselect
     */
    public void clearSelection(int index) {
        getSelectionModel().clearSelection(index);
    }
    
    /**
     * This method is available to test whether there are any selected indices/items. 
     * It will return true if there are no selected items, and false if there are.
     * @return <tt>true</tt> if there are no selected items, and <tt>false</tt> if there are
     */
    public boolean isEmpty() {
        return getSelectionModel().isEmpty();
    }
    
    /**
     * This method will return true if the given index is the currently selected 
     * index in the SingleSelectionModel.
     * @param index The index to check as to whether it is currently selected or not
     * @return <tt>true</tt> if the given index is selected, <tt>false</tt> otherwise.
     */
    public boolean isSelected(int index) {
        return getSelectionModel().isSelected(index);
    }
    
    /**
     * Selects the index for the first instance of given object in the underlying data model. 
     * Since the SingleSelectionModel can only support having a single index selected at a time, 
     * this also causes any previously selected index to be unselected.
     * @param item new item to be selected
     */
    public void select(T item) {
        getSelectionModel().select(item);
    }
    
    /**
     * Selects the given index. 
     * Since the SingleSelectionModel can only support having a single index selected 
     * at a time, this also causes any previously selected index to be unselected.
     * @param index index to select
     */
    public void select(int index) {
        getSelectionModel().select(index);
    }
    
    /**
     * Selects the first index.
     * Since the SingleSelectionModel can only support having a single index selected 
     * at a time, this also causes any previously selected index to be unselected.
     */
    public void selectFirst() {
        getSelectionModel().selectFirst();
    }
    
    /**
     * Selects the last index. 
     * Since the SingleSelectionModel can only support having a single index selected 
     * at a time, this also causes any previously selected index to be unselected.
     */
    public void selectLast() {
        getSelectionModel().selectLast();
    }
    
    /**
     * Selects the next index. 
     * Since the SingleSelectionModel can only support having a single index selected 
     * at a time, this also causes any previously selected index to be unselected.
     */
    public void selectNext() {
        getSelectionModel().selectNext();
    }
    
    /**
     * Selects the previous index. 
     * Since the SingleSelectionModel can only support having a single index selected 
     * at a time, this also causes any previously selected index to be unselected.
     */
    public void selectPrevious() {
        getSelectionModel().selectPrevious();
    }

    /**
     * Add the given style to this ComboBox's style. This method have 
     * no effect if the ComboBox already have the given style.
     * @param style the new style for this ComboBox
     */
    public void addStyleClass(String style) {
        if (!getStyleClass().contains(style)) getStyleClass().add(style);
    }
    
    /**
     * Add all the given styles to this ComboBox's style. This method doesn't 
     * add duplicates styles.
     * @param stylesCollection the new styles for this ComboBox list
     */
    public void addAllStyleClass(Collection<? extends String> stylesCollection) {
        stylesCollection.forEach(this::addStyleClass);
    }
    
    /**
     * Add all the given styles to this ComboBox's styles. This method doesn't 
     * add duplicates styles.
     * @param styles the new styles for this ComboBox
     */
    public void addAllStyleClass(String... styles) {
        for (String style : styles) addStyleClass(style);
    }
    
    /**
     * Returns <tt>true</tt> if this ComboxBox's styles contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this ComboxBox's styles contains
     * at least one style <tt>e</tt> such that
     * <tt>(style==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;style.equals(e))</tt>.
     *
     * @param style style to be tested
     * @return <tt>true</tt> if this ComboBox's styles contains the specified element
     */
    public boolean containsStyleClass(String style) {
        return getStyleClass().contains(style);
    }
    
    /**
     * Remove all the occurences of the given style to this ComboBox.
     * @param style the style to be remove
     */
    public void removeStyleClass(String style) {
        getStyleClass().removeAll(style);
    }
    
    /**
     * Remove all the occurences of the given styles to this ComboBox.
     * @param styles the styles to be remove
     */
    public void removeStylesClass(Collection<String> styles) {
        getStyleClass().removeAll(styles);
    }
    
    /**
     * Remove all the occurences of the given styles to this ComboBox.
     * @param styles the styles to be remove
     */
    public void removeStylesClass(String... styles) {
        getStyleClass().removeAll(styles);
    }
}
