package se3projecta.Persistence;

import java.util.ArrayList;
import java.util.List;

/**
 * This class includes a back-ported implementation of Java 1.7's "suppressed"
 * functionality
 *
 * The class also implements several non-standard helper methods for when using
 * the suppressed functionality
 *
 * @author Russell Peake <peak0042@flinders.edu.au>
 */
public class ImportException extends Throwable {

    /**
     * Whether or not any Exceptions have been suppressed.
     * @return whether or not any Exceptions have been suppressed
     */
    public boolean hasSuppressed() {
        return !suppressedExceptions.isEmpty();
    }
    /**
     * The list of suppressed exceptions, as returned by {@link
     * #getSuppressed()}. The list is initialized to a zero-element unmodifiable
     * sentinel list. When a serialized Throwable is read in, if the
     * {@code suppressedExceptions} field points to a zero-element list, the
     * field is reset to the sentinel value.
     *
     * @serial
     * @since 1.7
     */
    private List<Throwable> suppressedExceptions = new ArrayList<Throwable>(1);
    ;

    /** Message for trying to suppress oneself. */
    private static final String SELF_SUPPRESSION_MESSAGE = "Self-suppression not permitted";
    /**
     * Message for trying to suppress a null exception.
     */
    private static final String NULL_CAUSE_MESSAGE = "Cannot suppress a null exception.";

    /**
     * Appends the specified exception to the exceptions that were suppressed in
     * order to deliver this exception. This method is thread-safe and typically
     * called (automatically and implicitly) by the {@code try}-with-resources
     * statement.
     *
     * <p>The suppression behavior is enabled <em>unless</em> disabled null null null     {@linkplain #Throwable(String, Throwable, boolean, boolean) via
     * a constructor}. When suppression is disabled, this method does nothing
     * other than to validate its argument.
     *
     * <p>Note that when one exception {@linkplain
     * #initCause(Throwable) causes} another exception, the first exception is
     * usually caught and then the second exception is thrown in response. In
     * other words, there is a causal connection between the two exceptions.
     *
     * In contrast, there are situations where two independent exceptions can be
     * thrown in sibling code blocks, in particular in the {@code try} block of
     * a {@code try}-with-resources statement and the compiler-generated
     * {@code finally} block which closes the resource.
     *
     * In these situations, only one of the thrown exceptions can be propagated.
     * In the {@code try}-with-resources statement, when there are two such
     * exceptions, the exception originating from the {@code try} block is
     * propagated and the exception from the {@code finally} block is added to
     * the list of exceptions suppressed by the exception from the {@code try}
     * block. As an exception unwinds the stack, it can accumulate multiple
     * suppressed exceptions.
     *
     * <p>An exception may have suppressed exceptions while also being caused by
     * another exception. Whether or not an exception has a cause is
     * semantically known at the time of its creation, unlike whether or not an
     * exception will suppress other exceptions which is typically only
     * determined after an exception is thrown.
     *
     * <p>Note that programmer written code is also able to take advantage of
     * calling this method in situations where there are multiple sibling
     * exceptions and only one can be propagated.
     *
     * @param exception the exception to be added to the list of suppressed
     * exceptions
     * @throws IllegalArgumentException if {@code exception} is this throwable;
     * a throwable cannot suppress itself.
     * @throws NullPointerException if {@code exception} is {@code null}
     * @since 1.7
     */
    public final synchronized void addSuppressed(Throwable exception) {
        if (exception == this) {
            throw new IllegalArgumentException(SELF_SUPPRESSION_MESSAGE);
        }

        if (exception == null) {
            throw new NullPointerException(NULL_CAUSE_MESSAGE);
        }

        if (suppressedExceptions == null) { // Suppressed exceptions not recorded 
            return;
        }

        suppressedExceptions.add(exception);
    }
    private static final Throwable[] EMPTY_THROWABLE_ARRAY = new Throwable[0];

    /**
     * Returns an array containing all of the exceptions that were suppressed,
     * typically by the {@code try}-with-resources statement, in order to
     * deliver this exception.
     *
     * If no exceptions were suppressed or {@linkplain
     * #Throwable(String, Throwable, boolean, boolean) suppression is
     * disabled}, an empty array is returned. This method is thread-safe. Writes
     * to the returned array do not affect future calls to this method.
     *
     * @return an array containing all of the exceptions that were suppressed to
     * deliver this exception.
     * @since 1.7
     */
    public final synchronized Throwable[] getSuppressed() {
        if (suppressedExceptions.isEmpty()) {
            return EMPTY_THROWABLE_ARRAY;
        } else {
            return suppressedExceptions.toArray(EMPTY_THROWABLE_ARRAY);
        }
    }
}
