(ns isis.geom.action.in-plane-dispatch
  "The dispatcher of rules for the in-plane constraint.
  In in-plane constraint means that there are two markers,
  one representing a point, M_1, and a second representing a
  plane, M_2.  The point is constrained to lie in the plane."
  (:require [isis.geom.position-dispatch :as ms]
            [isis.geom.model.invariant :as invariant]
            [isis.geom.action
             [in-plane-o2p-slice :as o2p]
             [in-plane-p2o-slice :as p2o]]
            [clojure.pprint :as pp]))


(defn precondition
  "Associated with each constraint type is a function which
  checks the preconditions and returns the marker which
  is under-constrained followed by the marker that is fully-constrained.
  The :motive refers to the first marker, the point, thus

  :p2o indicates that the point *is-not* fully-constrained
  but the plane *is* fully-constrained.

  :o2p indicates that the plane *is-not* fully-constrained
  but the point *is* fully-constrained.

  nil indicates that there are insufficient constraints to
  make any inference."
  [kb point plane]
  (cond (invariant/marker-position? kb plane) [kb point plane :p2o]
        (and (invariant/marker-position? kb point)
             (invariant/marker-direction? kb point)) [kb point plane :o2p]
        :else nil))

(defn assemble-dispatch
  [kb point plane motive]
  (let [[[link-name _] _] (case motive
                            :o2p plane
                            :p2o point)
        link @(get (:link kb) link-name)
        tdof (get-in link [:tdof :#])
        rdof (get-in link [:rdof :#]) ]
    #_(pp/pprint ["in-plane assemble!" (str tdof ":" rdof "-" motive)
                  "point" point "plane" plane])
    {:tdof tdof :rdof rdof :motive motive}))

(defmulti assemble!
  "Transform the links and kb so that the constraint is met.
  Examine the underconstrained marker to determine the dispatch key.
  The key is the [#tdof #rdof] of the m2 link."
  assemble-dispatch
  :default nil)


(defmethod ms/constraint-attempt?
  :in-plane
  [kb constraint]
  (let [{m1 :m1 m2 :m2} constraint
        precon (precondition kb m1 m2) ]
    (if-not precon
      :pre-condition-not-met
      (let [[kb+ m1+ m2+ motive] precon]
        (try
          (ms/show-constraint kb+ "in-plane"
                              assemble-dispatch
                              m1+ m2+ motive)
          (assemble! kb+ m1+ m2+ motive)
          (catch Exception ex
            (ms/dump ex assemble-dispatch
                     "in-plane" kb+ m1+ m2+ motive)
            :exception-thrown))))))


(ms/defmethod-transform assemble! {:o2p 'o2p :p2o 'p2o})

