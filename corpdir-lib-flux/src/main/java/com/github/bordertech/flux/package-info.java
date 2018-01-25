/**
 * FLUX application architecture.
 * <p>
 * Flux is a word made up to describe "one way" data flow with very specific events and listeners. Data in a Flux
 * application flows in a single direction:
 * <pre>
 * {@code
 *     read-api                                 update-api
 *         |                                         |
 *  ->- Action -> Dispatcher -> Store -> View -> ActionCreator->-
 *  -<----<----<----<----<----<----<----<----<----<----<----<----
 * }
 * </pre>
 * <p>
 * Quoting the  <a href="http://facebook.github.io/flux/docs/overview.html">official doco</a>:-
 * <blockquote>
 * Flux applications have three major parts: the dispatcher, the stores, and the views. These should not be confused
 * with Model-View-Controller. Controllers do exist in a Flux application, but they are controller-views — views often
 * found at the top of the hierarchy that retrieve data from the stores and pass this data down to their children.
 * Additionally, action creators — dispatcher helper methods — are used to support a semantic API that describes all
 * changes that are possible in the application. It can be useful to think of them as a fourth part of the Flux update
 * cycle.
 * </blockquote>
 * <p>
 * A View dispatches an Action (usually via an ActionCreator) to a Store via the Dispatcher. The state of a Store can
 * only be changed via an Action. When a Store has a state change it dispatches a change event to registered Views.
 * </p>
 *
 * @see <a href="http://facebook.github.io/flux/docs/overview.html">Flux official doco</a>
 * @see <a href="https://blog.andrewray.me/flux-for-stupid-people">Handy flux blog</a>
 * @see <a href="https://scotch.io/tutorials/getting-to-know-flux-the-react-js-architecture">Handy flux tutorial</a>
 */
package com.github.bordertech.flux;
