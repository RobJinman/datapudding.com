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
   [datapudding.config :refer [app-config]]
   [datapudding.navigation :as nav]))


(enable-console-print!)


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
   {:db {:navigation {:route [:blog]}}}))


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

(defn nav-item-view
  [l10n id]
  [:div {:class "nav-item"
         :on-click #(dispatch [:navigation/set-route [id]])}
   (id l10n)])

(defn navigation-view
  [l10n]
  [:div {:class "navbar"}
   [nav-item-view l10n :about]
   [nav-item-view l10n :portfolio]
   [nav-item-view l10n :blog]
   [nav-item-view l10n :contact]])

(defn header-view
  [l10n]
  [:div {:class "header"}
   [:img {:src "img/datapudding.svg"}]
   [navigation-view (:navigation l10n)]])

(defn about-view
  []
  [:div {:class "about"}
   "About page"])

(defn portfolio-view
  []
  [:div {:class "portfolio"}
   "Portfolio page"])

(def blog-content
  [{:date "20/01/2018"
    :title "Welcome to Datapudding.com"
    :summary "This is my"
    :content "This is my new website."}
   {:date "21/01/2018"
    :title "An interesting article"
    :summary "This is very interesting"
    :content "This is very interesting, isn't it?"}])

(def state-graph
  {:about {}
   :portfolio {}
   :blog {}
   :contact {}})

(defn blog-view
  []
  (let [route (subscribe [:navigation/current])]
    (fn []
      [:div {:class "blog"}
       "Blog"
       (for [{:keys [date title summary]} blog-content]
         [:div {:class "article-summary"}
          [:div {:class "date"} date]
          [:div {:class "title"
                 :on-click #(dispatch [:navigation/])} title]
          [:div {:class "summary"} summary]])])))

(defn contact-view
  []
  [:div {:class "contact"}
   "Contact page"])

(defn content-view
  [l10n]
  (let [route (subscribe [:navigation/current])]
    (fn [l10n]
      [:div {:class "page-content"}
       (println @route)
       (condp nav/matches @route
         [:about] [about-view]
         [:portfolio] [portfolio-view]
         [:blog] [blog-view]
         [:contact] [contact-view])])))

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
