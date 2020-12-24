(ns christmas.chr20.day18
  (:require [clojure.string :as str]
            [christmas.tools :as t]
            [clojure.set :as s]))


(defn parsefile [filename]
  (->> (slurp filename)
       (str/split-lines)
       (map #(re-seq #"\(|\)|\d+|\+|\*" %))))

(def signs {"+" + "*" *})

(defn eva [[a x b]]
  [((signs x) a b)])

(defn replace-last [pieces new-value]
  (assoc pieces (dec (count pieces)) new-value))

(defn check [pieces]
  (if (= 3 (count (last pieces)))
    (replace-last pieces (eva (last pieces)))
    pieces))

(defn number [result input]
  (->> (t/parse-int (str input))
       (t/add-to-last result)
       (check)))

(defn sign [result input]
  (t/add-to-last result input))

(defn open [result _]
  (conj result []))

(defn close [result _]
  (-> (subvec result 0 (dec (count result)))
      (number (t/llast result))))

(def actions {"+" sign "(" open ")" close "*" sign})

(defn tick [result input]
  (->> (actions input number)
       (#(% result input))))

(defn day18 [input]
  (->> (parsefile input)
       (map #(reduce tick [[]] %))
       (map t/llast)
       (apply +)))

(defn last-three [last-piece]
  (->> (count last-piece)
       (#(subvec last-piece (- % 3) %))))

(defn eval-end [[front back]]
  (vec (concat (vec front) (eva back))))

(defn do-the-math [pieces]
  (->> (eval-end (split-at (- (count (last pieces)) 3) (last pieces)))
       (replace-last pieces)))

(defn check-b [pieces]
  (if (= "+" (t/second-last (last pieces)))
    (do-the-math pieces)
    pieces))

(defn number-b [result input]
  (->> (t/parse-int (str input))
       (t/add-to-last result)
       (check-b)))

(defn move-last-to-second-last [pieces]
  (t/add-to-last (vec (butlast pieces)) (t/llast pieces)))

(defn close-b [pieces _]
  (if (= 1 (count (last pieces)))
    (check-b (move-last-to-second-last pieces))
    (->> (iterate do-the-math pieces)
         (t/find-first #(= 1 (count (last %))))
         (move-last-to-second-last)
         (check-b))))

(def actions-b {"+" sign "(" open ")" close-b "*" sign})

(defn tick-b [result input]
  (->> (actions-b input number-b)
       (#(% result input))))

(defn end [result]
  (if (= 1 (count (flatten result)))
    (first (flatten result))
    (->> (apply concat result)
         (vec)
         (vector)
         (iterate do-the-math)
         (t/find-first #(= 1 (count (last %))))
         (t/llast))))

(defn day18b [input]
  (->> (parsefile input)
       (map #(reduce tick-b [[]] %))
       (map end)
       (apply +)))
