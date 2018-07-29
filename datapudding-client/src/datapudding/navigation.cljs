(ns datapudding.navigation
  (:require
   [goog.dom :as gdom]
   [reagent.core :as reagent]
   [re-frame.core :refer [reg-event-db
                          reg-event-fx
                          reg-fx
                          reg-cofx
                          inject-cofx
                          reg-sub
                          dispatch
                          dispatch-sync
                          subscribe]]))

(def pages {:about {}
            :blog {:articles [{:date ""
                               :title ""
                               :content ""}
                              {:date ""
                               :title ""
                               :content ""}]}})

(reg-event-db
 :navigation/set-route
 (fn [db [_ route]]
   (assoc-in db [:navigation :route] route)))

(reg-sub
 :navigation/current
 (fn [db _]
   (get-in db [:navigation :route])))

(defn- consecutive?
  [v]
  (every? #(= (inc (first %))
              (last %))
          (partition 2 1 v)))

(defn matches
  [part route]
  (if (< (count part) 2)
    (not= -1 (.indexOf route (first part)))
    (-> (map #(.indexOf route %) part)
        consecutive?)))
