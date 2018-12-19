(ns christmas.day19-test
 (:require [christmas.day19 :as chr]
  		   [expectations :refer :all]
  		   [clojure.string :as str]))


(defn parse-int [thing]
	(Integer/parseInt thing))

(defn handle-row [my-string]
	(-> (re-seq #"\d+" my-string)
		(#(mapv parse-int %))))

;(def file "resources/day16-20/Input17")
;(def fe-file "resources/day16-20/Input17failingExample")
;(def mini-file "resources/day16-20/Input17failingMiniExample")
;(def real-file "resources/day16-20/Input17real")
