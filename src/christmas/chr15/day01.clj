(ns christmas.chr15.day01
  (:require [clojure.string :as str]))

(defn day01 [input]
	(->> (frequencies input)
		 (#(- (% \() (% \))))))

(def value {\( 1 \) -1})

(defn day01b [input]
	(->> (reductions #(+ %1 (value %2)) 0 input)
		 (take-while (partial < -1))
		 count))

