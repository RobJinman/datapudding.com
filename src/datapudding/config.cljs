(ns datapudding.config
  (:require [datapudding.localisation :refer [get-l10n!]]))

(def app-config {:l10n (get-l10n!)})
