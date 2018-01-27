(ns datapudding.core
  (:require-macros
   [datapudding.macros :refer [code-critic]])
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
                          subscribe]]
   [datapudding.config :refer [app-config]]))


;; -- Definitions ---------------------------------------------------------



;; -- Pure Helper Functions -----------------------------------------------



;; -- Impure Helper Functions ---------------------------------------------

(defn posix-time!
  []
  (/ (.getTime (js/Date.)) 1000))


;; -- Coeffect Handlers ---------------------------------------------------

(reg-cofx
 :posix-time
 (fn [cofx]
   (assoc cofx :posix-time (posix-time!))))


;; -- Effect Handlers -----------------------------------------------------

(reg-fx
 :do-side-effect
 (fn [{:keys [arg1 arg2]}]
   (do))) ;; TODO


;; -- Event Handlers ------------------------------------------------------

(reg-event-fx
 :initialise
 (fn [cofx _]
   {:db {:msg "Hello, World!"}}))


;; -- Subscription Handlers -----------------------------------------------

(reg-sub
 :msg
 (fn [state _]
   (get-in state [:msg])))


;; -- View Components -----------------------------------------------------

(defn sub-view
  []
  (let [msg (subscribe [:msg])]
    [:div (str @msg)]))

(defn header-view
  [l10n]
  [:div {:class "header"}
   [:img {:src "img/datapudding.svg"}]])

(defn content-view
  [l10n]
  [:div {:class "content-view"}])

(defn footer-view
  [l10n]
  [:div {:class "footer"}
   (:copyright l10n)])

(defn main-view
  [{:keys [l10n] :as config}]
  (fn [{:keys [l10n] :as config}]
    [:div {:class "page"}
     [:div {:class "content-wrap"}
      [header-view (:header l10n)]
      [content-view (:content l10n)]]
     [footer-view (:footer l10n)]]))


;; -- Entry Point ---------------------------------------------------------

(defn ^:export run
  []
  (dispatch-sync [:initialise])
  (reagent/render [main-view app-config]
                  (gdom/getElement "datapudding-root")))
