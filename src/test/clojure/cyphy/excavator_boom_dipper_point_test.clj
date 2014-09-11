(ns cyphy.excavator-boom-dipper-point-test
  (:require [midje.sweet :refer [defchecker chatty-checker checker facts fact]]
            [isis.geom.cyphy.cad-stax :as cyphy]

            [clojure.java.io :as jio]
            [clojure.data]
            [clojure.pprint :as pp]
            [isis.geom.model.meta-constraint :as meta-con]
            [isis.geom.machine.misc :as misc]

            [isis.geom.analysis
             [position-analysis :refer [position-analysis]]]

            [isis.geom.machine.misc :as misc]
            [isis.geom.position-dispatch
             :refer [constraint-attempt?]]
            [isis.geom.action
             [coincident-slice]
             [helical-slice]
             [in-line-slice]
             [in-plane-slice]
             [offset-x-slice]
             [offset-z-slice]
             [parallel-z-slice]]))


(defchecker ref->checker
  "A checker that allows the names of references to be ignored."
  [expected]
  (checker [actual]
           (let [actual-deref (clojure.walk/postwalk
                               #(if (misc/reference? %) [:ref @%] %) actual)]
             (if (= actual-deref expected) true
               (do
                 (clojure.pprint/pprint ["Actual result:" actual-deref])
                 (clojure.pprint/pprint ["Expected result:" expected])
                 )))))

(with-open [fis (-> "excavator/excavator_boom_dipper_point.xml"
                    jio/resource jio/input-stream)]
  (let [kb (cyphy/extract-knowledge-from-cad-assembly fis)
        constraints (:constraint kb)
        constraints-exp (meta-con/expand-collection constraints)

        ;; _ (pp/pprint ["exp-con:" exp-constraints])

        constraint-checker
        (ref->checker
         '[{:m1 [["{059166f0-b3c0-474f-9dcb-d5e865754d77}|1" "TOP"]
                 {:e [1.0 0.0 0.0]}]
            :m2 [["{bb160c79-5ba3-4379-a6c1-8603f29079f2}" "TOP"]
                 {:e [1.0 0.0 0.0]}]
            :type :coincident}
           {:m1 [["{059166f0-b3c0-474f-9dcb-d5e865754d77}|1" "RIGHT"]
                 {:e [0.0 1.0 0.0]}]
            :m2 [["{bb160c79-5ba3-4379-a6c1-8603f29079f2}" "RIGHT"]
                 {:e [0.0 1.0 0.0]}]
            :type :coincident}
           {:m1 [["{059166f0-b3c0-474f-9dcb-d5e865754d77}|1" "FRONT"]
                 {:e [0.0 0.0 1.0]}]
            :m2 [["{bb160c79-5ba3-4379-a6c1-8603f29079f2}" "FRONT"]
                 {:e [0.0 0.0 1.0]}]
            :type :coincident}

           {:type :point
            :m1 [["{62243423-b7fd-4a10-8a98-86209a6620a4}" "APNT_2"]
                 {:e [-8649.51 4688.51 600.0], :q [0.0 0.0 0.0], :pi 0.0}],
            :m2 [["{bb160c79-5ba3-4379-a6c1-8603f29079f2}" "PNT2"]
                 {:e [3467.85 43.0687 302.5], :q [0.0 0.0 0.0], :pi 0.0}]}
           {:type :point,
            :m1 [["{62243423-b7fd-4a10-8a98-86209a6620a4}" "APNT_1"]
                 {:e [-8625.71 4720.65 570.0], :q [0.0 0.0 0.0], :pi 0.0}],
            :m2 [["{bb160c79-5ba3-4379-a6c1-8603f29079f2}" "PNT1"]
                 {:e [3455.57 5.0 332.5], :q [0.0 0.0 0.0], :pi 0.0}]}
           {:type :point,
            :m1 [["{62243423-b7fd-4a10-8a98-86209a6620a4}" "APNT_0"]
                 {:e [-8625.71 4720.65 600.0], :q [0.0 0.0 0.0], :pi 0.0}],
            :m2 [["{bb160c79-5ba3-4379-a6c1-8603f29079f2}" "PNT0"]
                 {:e [3455.57 5.0 302.5], :q [0.0 0.0 0.0], :pi 0.0}]} ] )


        expanded-constraint-checker
        (ref->checker
         '[{:type :coincident
            :m1 [["{059166f0-b3c0-474f-9dcb-d5e865754d77}|1" "TOP"]
                 {:e [1.0 0.0 0.0]}]
            :m2 [["{bb160c79-5ba3-4379-a6c1-8603f29079f2}" "TOP"]
                 {:e [1.0 0.0 0.0]}]}
           {:type :coincident
            :m1 [["{059166f0-b3c0-474f-9dcb-d5e865754d77}|1" "RIGHT"]
                 {:e [0.0 1.0 0.0]}]
            :m2 [["{bb160c79-5ba3-4379-a6c1-8603f29079f2}" "RIGHT"]
                 {:e [0.0 1.0 0.0]}]}
           {:type :coincident
            :m1 [["{059166f0-b3c0-474f-9dcb-d5e865754d77}|1" "FRONT"]
                 {:e [0.0 0.0 1.0]}]
            :m2 [["{bb160c79-5ba3-4379-a6c1-8603f29079f2}" "FRONT"]
                 {:e [0.0 0.0 1.0]}]}
           {:type :coincident,
            :m1 [["{62243423-b7fd-4a10-8a98-86209a6620a4}" "APNT_2"]
                 {:e [-8649.51 4688.51 600.0], :q [0.0 0.0 0.0], :pi 0.0}],
            :m2 [["{bb160c79-5ba3-4379-a6c1-8603f29079f2}" "PNT2"]
                 {:e [3467.85 43.0687 302.5], :q [0.0 0.0 0.0], :pi 0.0}]}
           {:type :coincident,
            :m1 [["{62243423-b7fd-4a10-8a98-86209a6620a4}" "APNT_1"]
                 {:e [-8625.71 4720.65 570.0], :q [0.0 0.0 0.0], :pi 0.0}],
            :m2 [["{bb160c79-5ba3-4379-a6c1-8603f29079f2}" "PNT1"]
                 {:e [3455.57 5.0 332.5], :q [0.0 0.0 0.0], :pi 0.0}]}
           {:type :coincident,
            :m1 [["{62243423-b7fd-4a10-8a98-86209a6620a4}" "APNT_0"]
                 {:e [-8625.71 4720.65 600.0], :q [0.0 0.0 0.0], :pi 0.0}],
            :m2 [["{bb160c79-5ba3-4379-a6c1-8603f29079f2}" "PNT0"]
                 {:e [3455.57 5.0 302.5], :q [0.0 0.0 0.0], :pi 0.0}]}]  )


        link-checker
        (ref->checker
         '{"{62243423-b7fd-4a10-8a98-86209a6620a4}"
           [:ref
            {:versor {:xlate [0.0 0.0 0.0], :rotate [1.0 0.0 0.0 0.0]},
             :tdof {:# 3},
             :rdof {:# 3}}],
           "{bb160c79-5ba3-4379-a6c1-8603f29079f2}"
           [:ref
            {:versor {:xlate [0.0 0.0 0.0], :rotate [1.0 0.0 0.0 0.0]},
             :tdof {:# 3},
             :rdof {:# 3}}],
           "{059166f0-b3c0-474f-9dcb-d5e865754d77}|1"
           [:ref
            {:versor {:xlate [0.0 0.0 0.0], :rotate [1.0 0.0 0.0 0.0]},
             :tdof {:# 0},
             :rdof {:# 0}}]} )

        link-checker-2
        (ref->checker
         '{"{62243423-b7fd-4a10-8a98-86209a6620a4}"
           [:ref
            {:versor
             {:xlate [12315.409884054143 -4259.980462767625 903.0008418651848],
              :rotate
              [4.9967957578778144E-5
               -0.8894325067015925
               0.4570665300942336
               -2.435861248047122E-5]},
             :tdof {:# 0, :point [3467.8500000000004 43.06869999999981 302.5]},
             :rdof {:# 0}}],
           "{bb160c79-5ba3-4379-a6c1-8603f29079f2}"
           [:ref
            {:versor {:xlate [0.0 0.0 0.0], :rotate [1.0 0.0 0.0 0.0]},
             :tdof {:# 0, :point [1.0 0.0 0.0]},
             :rdof {:# 0}}],
           "{059166f0-b3c0-474f-9dcb-d5e865754d77}|1"
           [:ref
            {:versor {:xlate [0.0 0.0 0.0], :rotate [1.0 0.0 0.0 0.0]},
             :tdof {:# 0},
             :rdof {:# 0}}]} )


        mark-checker
        (ref->checker
         '{:loc [:ref #{["{059166f0-b3c0-474f-9dcb-d5e865754d77}|1"]}],
           :z [:ref #{["{059166f0-b3c0-474f-9dcb-d5e865754d77}|1"]}],
           :x [:ref #{["{059166f0-b3c0-474f-9dcb-d5e865754d77}|1"]}]} )

        mark-checker-2
        (ref->checker
         '
         {:loc
          [:ref
           #{["{059166f0-b3c0-474f-9dcb-d5e865754d77}|1"]
             ["{62243423-b7fd-4a10-8a98-86209a6620a4}"]
             ["{bb160c79-5ba3-4379-a6c1-8603f29079f2}"]}],
          :z
          [:ref
           #{["{059166f0-b3c0-474f-9dcb-d5e865754d77}|1"]
             ["{62243423-b7fd-4a10-8a98-86209a6620a4}"]
             ["{bb160c79-5ba3-4379-a6c1-8603f29079f2}"]}],
          :x
          [:ref
           #{["{059166f0-b3c0-474f-9dcb-d5e865754d77}|1"]
             ["{62243423-b7fd-4a10-8a98-86209a6620a4}"]
             ["{bb160c79-5ba3-4379-a6c1-8603f29079f2}"]}]} )


        success-checker
        (ref->checker
         '
         [{:type :coincident,
           :m1
           [["{059166f0-b3c0-474f-9dcb-d5e865754d77}|1" "TOP"]
            {:e [1.0 0.0 0.0]}],
           :m2
           [["{bb160c79-5ba3-4379-a6c1-8603f29079f2}" "TOP"]
            {:e [1.0 0.0 0.0]}]}
          {:type :coincident,
           :m1
           [["{059166f0-b3c0-474f-9dcb-d5e865754d77}|1" "RIGHT"]
            {:e [0.0 1.0 0.0]}],
           :m2
           [["{bb160c79-5ba3-4379-a6c1-8603f29079f2}" "RIGHT"]
            {:e [0.0 1.0 0.0]}]}
          {:type :coincident,
           :m1
           [["{059166f0-b3c0-474f-9dcb-d5e865754d77}|1" "FRONT"]
            {:e [0.0 0.0 1.0]}],
           :m2
           [["{bb160c79-5ba3-4379-a6c1-8603f29079f2}" "FRONT"]
            {:e [0.0 0.0 1.0]}]}
          {:type :coincident,
           :m1
           [["{62243423-b7fd-4a10-8a98-86209a6620a4}" "APNT_2"]
            {:e [-8649.51 4688.51 600.0], :q [0.0 0.0 0.0], :pi 0.0}],
           :m2
           [["{bb160c79-5ba3-4379-a6c1-8603f29079f2}" "PNT2"]
            {:e [3467.85 43.0687 302.5], :q [0.0 0.0 0.0], :pi 0.0}]}
          {:type :coincident,
           :m1
           [["{62243423-b7fd-4a10-8a98-86209a6620a4}" "APNT_1"]
            {:e [-8625.71 4720.65 570.0], :q [0.0 0.0 0.0], :pi 0.0}],
           :m2
           [["{bb160c79-5ba3-4379-a6c1-8603f29079f2}" "PNT1"]
            {:e [3455.57 5.0 332.5], :q [0.0 0.0 0.0], :pi 0.0}]}
          {:type :coincident,
           :m1
           [["{62243423-b7fd-4a10-8a98-86209a6620a4}" "APNT_0"]
            {:e [-8625.71 4720.65 600.0], :q [0.0 0.0 0.0], :pi 0.0}],
           :m2
           [["{bb160c79-5ba3-4379-a6c1-8603f29079f2}" "PNT0"]
            {:e [3455.57 5.0 302.5], :q [0.0 0.0 0.0], :pi 0.0}]}] )


        failure-checker (ref->checker '[])
        ]


    (facts "about the parsed cad-assembly file with points"
           (fact "about the constraints" constraints => constraint-checker)
           (fact "about the initial link settings" (:link kb) => link-checker)
           (fact "about the base link id" (:base kb) => "{059166f0-b3c0-474f-9dcb-d5e865754d77}|1")
           (fact "about the initial marker invariants" (:mark kb) => mark-checker)


           (fact "about the expanded constraints" constraints-exp => expanded-constraint-checker))


    (let [result (position-analysis kb constraints-exp)
          [success? result-kb result-success result-failure] result
          {result-mark :mark result-link :link} result-kb ]

      ;; (pp/pprint result-success)
      ;; (pp/pprint result-link)
      (facts "about results of linkage-assembly"
             (fact "about the mark result" result-mark => mark-checker-2)
             (fact "about the link result" result-link => link-checker-2)
             (fact "about the success result" result-success => success-checker)
             (fact "about the failure result" result-failure => failure-checker) )

      #_(with-open [fis (-> "excavator/excavator_boom_dipper_point.xml"
                            jio/resource jio/input-stream)
                    fos (-> "/tmp/excavator_boom_dipper_aug.xml"
                            jio/output-stream)]

          (cyphy/update-cad-assembly-using-knowledge fis fos kb) ) ) ))

;;    (pp/pprint (ref->str result-link))
