(ns christmas.chr15.day02
  (:require [clojure.string :as str]))

(defn wrapper [[b l h]]
	(->> (vector [b l][l h][h b])
		 (map (partial apply *))
		 (#(+ (apply min %) (* 2 (apply + %))))))

(defn day02 [input]
	(->> (map wrapper input)
		 (apply +)))

(defn ribbon [dimensions]
	(->> (sort > dimensions)
		 rest
		 (apply +)
		 (* 2)
		 (+ (apply * dimensions))))

(defn day02b [input]
	(->> (map ribbon input)
		 (apply +)))
